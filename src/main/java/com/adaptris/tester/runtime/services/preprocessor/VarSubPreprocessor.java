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


import com.adaptris.core.CoreException;
import com.adaptris.core.varsub.Constants;
import com.adaptris.core.varsub.VariableSubstitutionPreProcessor;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.utils.FsHelper;
import com.adaptris.util.KeyValuePair;
import com.adaptris.util.KeyValuePairSet;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @service-test-config variable-substitution-preprocessor
 */
@XStreamAlias("variable-substitution-preprocessor")
public class VarSubPreprocessor implements Preprocessor {

  @XStreamImplicit(itemFieldName = "property-file")
  private List<String> propertyFile;

  public VarSubPreprocessor(){
    setPropertyFile(new ArrayList<String>());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input, ServiceTestConfig config) throws PreprocessorException {
    try {
      VariableSubstitutionPreProcessor processor = new VariableSubstitutionPreProcessor(createPropertyFileSet(config));
      return processor.process(input);
    } catch (CoreException e) {
      throw new PreprocessorException("Failed to substitute variables", e);
    }
  }

  public void setPropertyFile(List<String> propertyFile) {
    this.propertyFile = propertyFile;
  }

  public List<String> getPropertyFile() {
    return propertyFile;
  }

  public void addPropertyFile(String propertyFile){
    this.propertyFile.add(propertyFile);
  }

  private KeyValuePairSet createPropertyFileSet(ServiceTestConfig config) throws PreprocessorException{
    if (propertyFile.size() == 0){
      throw new PreprocessorException("At least one properties file must be set");
    }
    try {
      KeyValuePairSet kvp = new KeyValuePairSet();
      for (int i = 0; i < propertyFile.size(); i++) {
        File file = FsHelper.createFile(propertyFile.get(i), config);
        kvp.addKeyValuePair(new KeyValuePair(Constants.VARSUB_PROPERTIES_URL_KEY + "." + i, "file:///" + file.getAbsolutePath()));
      }
      return kvp;
    } catch (IOException | URISyntaxException e) {
      throw new PreprocessorException("Failed to create varsub path", e);
    }
  }
}
