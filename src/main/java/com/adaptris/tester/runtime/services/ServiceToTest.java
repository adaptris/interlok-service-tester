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

package com.adaptris.tester.runtime.services;

import java.util.ArrayList;
import java.util.List;

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.services.preprocessor.Preprocessor;
import com.adaptris.tester.runtime.services.preprocessor.PreprocessorException;
import com.adaptris.tester.runtime.services.sources.Source;
import com.adaptris.tester.runtime.services.sources.SourceException;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config service-to-test
 */
@XStreamAlias("service-to-test")
public class ServiceToTest {

  private Source source;
  private List<Preprocessor> preprocessors;

  public ServiceToTest(){
    setPreprocessors(new ArrayList<Preprocessor>());
  }

  public void setSource(Source source) {
    this.source = source;
  }

  public Source getSource() {
    return source;
  }

  public void setPreprocessors(List<Preprocessor> preprocessors) {
    this.preprocessors = preprocessors;
  }

  public List<Preprocessor> getPreprocessors() {
    return preprocessors;
  }

  public void addPreprocessor(Preprocessor preprocessor){
    preprocessors.add(preprocessor);
  }

  public String getProcessedSource(ServiceTestConfig config) throws PreprocessorException, SourceException {
    String result = getSource().getSource(config);
    for (Preprocessor preprocessor : getPreprocessors()) {
      result = preprocessor.execute(result, config);
    }
    return result;
  }
}
