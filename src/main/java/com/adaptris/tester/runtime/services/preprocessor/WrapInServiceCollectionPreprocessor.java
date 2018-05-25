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

import com.adaptris.util.GuidGenerator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config wrap-in-sc-preprocessor
 */
@XStreamAlias("wrap-in-sc-preprocessor")
public class WrapInServiceCollectionPreprocessor implements Preprocessor{

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input) throws PreprocessorException {
    GuidGenerator guidGenerator = new GuidGenerator();
    String guid = guidGenerator.getUUID();
    String start = "<service-collection class=\"service-list\"><unique-id>sc-wrap-" + guid +"</unique-id><services>";
    String finish = "</services></service-collection>";
    return start + input + finish;
  }
}
