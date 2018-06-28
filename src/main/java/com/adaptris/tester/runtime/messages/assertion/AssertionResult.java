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

package com.adaptris.tester.runtime.messages.assertion;

/**
 * Class used to store assertion results.
 */
public class AssertionResult {

  private final String uniqueId;
  private final String type;
  private final boolean passed;
  private String message;

  public AssertionResult(String uniqueId, String type, boolean passed){
    this.uniqueId = uniqueId;
    this.type = type;
    this.passed = passed;
  }

  public AssertionResult(String uniqueId, String type, boolean passed, String message){
    this(uniqueId, type, passed);
    this.message = message;
  }

  /**
   * Returns whether assertion passed.
   * @return Did assertion pass.
   */
  public boolean isPassed() {
    return passed;
  }

  /**
   * Returns message, if message is null returns: "Assertion Failure [type]".
   * @return The assertion message.
   */
  public String getMessage() {
    if(message != null){
      return message;
    } else {
      return "Assertion Failure: [" + type + "]";
    }
  }
}
