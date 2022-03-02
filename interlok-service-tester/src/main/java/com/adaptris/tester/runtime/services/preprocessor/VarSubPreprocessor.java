/*
    Copyright 2018 Adaptris Ltd.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.adaptris.tester.runtime.services.preprocessor;

import static com.adaptris.tester.runtime.ServiceTestConfig.SERVICE_TESTER_WORKING_DIRECTORY;
import static com.adaptris.tester.runtime.services.preprocessor.Preprocessor.wrapException;

import com.adaptris.core.varsub.Constants;
import com.adaptris.core.varsub.VariableSubstitutionPreProcessor;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.utils.FsHelper;
import com.adaptris.util.KeyValuePair;
import com.adaptris.util.KeyValuePairSet;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.io.FileDeleteStrategy;

/**
 *
 * @service-test-config variable-substitution-preprocessor
 */
@XStreamAlias("variable-substitution-preprocessor")
public class VarSubPreprocessor implements Preprocessor {
  private static transient final FileCleaningTracker cleaner = new FileCleaningTracker();

  @XStreamImplicit(itemFieldName = "property-file")
  @Getter
  @Setter
  private List<String> propertyFile;

  public VarSubPreprocessor(){
    setPropertyFile(new ArrayList<>());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input, ServiceTestConfig config) throws PreprocessorException {
    try {
      VariableSubstitutionPreProcessor processor = new VariableSubstitutionPreProcessor(createPropertyFileSet(config));
      return processor.process(input);
    } catch (Exception e) {
      throw wrapException("Failed to substitute variables", e);
    }
  }

  public void addPropertyFile(String propertyFile){
    this.propertyFile.add(propertyFile);
  }

  // Create a
  // service.tester.working.directory=XXXX property so we can refer to it
  // if it's not already been defined.
  private File createServiceTesterWorkingDirProperty(ServiceTestConfig config) throws Exception {
    File result =createTrackedFile(this);
    Properties properties = new Properties();
    properties.put(SERVICE_TESTER_WORKING_DIRECTORY, config.workingDirectory.getAbsolutePath());
    try (FileOutputStream out = new FileOutputStream(result)) {
      properties.store(out, "");
    }
    return result;
  }

  private KeyValuePairSet createPropertyFileSet(ServiceTestConfig config) throws PreprocessorException {
    try {
      if (propertyFile.size() == 0) {
        throw new PreprocessorException("At least one properties file must be set");
      }
      KeyValuePairSet kvp = new KeyValuePairSet();
      kvp.addKeyValuePair(new KeyValuePair(Constants.VARSUB_PROPERTIES_URL_KEY + ".00",
          "file:///" + createServiceTesterWorkingDirProperty(config).getAbsolutePath()));
      for (int i = 0; i < propertyFile.size(); i++) {
        File file = FsHelper.createFile(propertyFile.get(i), config);
        // flip out the index to be 1-based because we already have .0
        // Also are we going to have > 10 property files, well we might, but > 99 ?
        String key = String.format("%s.%02d", Constants.VARSUB_PROPERTIES_URL_KEY, i+1);
        kvp.addKeyValuePair(new KeyValuePair(key, "file:///" + file.getAbsolutePath()));
      }
      return kvp;
    } catch (Exception e) {
      throw wrapException("Failed to create varsub path", e);
    }
  }

  private static File createTrackedFile(Object tracker) throws IOException {
    File f = File.createTempFile("interlok-stwd", "", null);
    return trackFile(f, tracker);
  }

  private static File trackFile(File f, Object tracker) {
    cleaner.track(f, tracker, FileDeleteStrategy.FORCE);
    return f;
  }
}
