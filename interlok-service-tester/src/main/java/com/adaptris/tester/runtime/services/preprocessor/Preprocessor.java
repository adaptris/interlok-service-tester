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

public interface Preprocessor {

  /**
   * Execute the preprocessors against the service input returning updated service.
   *
   * @param input Service to process
   * @return Processed service
   * @throws PreprocessorException wraps any thrown Exception
   */
  String execute(String input, ServiceTestConfig config) throws PreprocessorException;

  static PreprocessorException wrapException(Exception e) {
    return wrapException(e.getMessage(), e);
  }

  static PreprocessorException wrapException(String msg, Exception e) {
    if (e instanceof PreprocessorException) {
      return (PreprocessorException) e;
    }
    return new PreprocessorException(msg, e);
  }
}
