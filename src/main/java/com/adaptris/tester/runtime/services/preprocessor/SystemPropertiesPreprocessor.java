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
import com.adaptris.core.varsub.SystemPropertiesPreProcessor;
import com.adaptris.util.KeyValuePairSet;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config system-properties-substitution-preprocessor
 */
@XStreamAlias("system-properties-substitution-preprocessor")
public class SystemPropertiesPreprocessor implements Preprocessor {


  public SystemPropertiesPreprocessor(){}

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input) throws PreprocessorException {
    try {
      SystemPropertiesPreProcessor processor = new SystemPropertiesPreProcessor(new KeyValuePairSet());
      return processor.process(input);
    } catch (CoreException e) {
      throw new PreprocessorException("Failed to substitute variables", e);
    }
  }
}
