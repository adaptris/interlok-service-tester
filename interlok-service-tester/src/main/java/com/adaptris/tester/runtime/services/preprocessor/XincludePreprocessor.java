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
import com.adaptris.core.xinclude.XincludePreProcessor;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.util.KeyValuePairSet;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config xinclude-preprocessor
 */
@XStreamAlias("xinclude-preprocessor")
public class XincludePreprocessor implements Preprocessor {

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input, ServiceTestConfig config) throws PreprocessorException {
    try {
      XincludePreProcessor processor = new XincludePreProcessor(new KeyValuePairSet());
      return processor.process(input);
    } catch (CoreException e) {
      throw new PreprocessorException("Failed to perform xinclude", e);
    }
  }
}
