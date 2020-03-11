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

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks the value of {@link TestMessage#getNextServiceId()} equals {@link #getValue()}.
 *
 * @service-test-config assert-next-service-id
 */
@XStreamAlias("assert-next-service-id")
public class AssertNextServiceId implements Assertion {

  @Deprecated
  private String uniqueId;
  private String value;

  @Override
  public AssertionResult execute(TestMessage actual, ServiceTestConfig config) throws ServiceTestException {
    return new AssertionResult("assert-next-service-id", actual.getNextServiceId().equals(getValue()));
  }

  @Override
  public String expected() {
    return "Next service id: " + getValue();
  }

  @Override
  public boolean showReturnedMessage() {
    return true;
  }

  /**
   * Set value to check next service id against.
   * @param value Value to check next service id against.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Get value to check next service id against.
   * @return Value to check next service id against.
   */
  public String getValue() {
    return value;
  }

}
