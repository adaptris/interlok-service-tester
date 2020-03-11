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

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.XpathCommon;
import com.adaptris.tester.runtime.XpathCommonException;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config xpath-preprocessor
 */
@XStreamAlias("xpath-preprocessor")
public class XpathPreprocessor extends XpathCommon implements Preprocessor {

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input, ServiceTestConfig config) throws PreprocessorException {
    try {
      return nodeToString(selectSingleNode(input));
    } catch (XpathCommonException e) {
      throw new PreprocessorException(e.getMessage(), e);
    }
  }
}
