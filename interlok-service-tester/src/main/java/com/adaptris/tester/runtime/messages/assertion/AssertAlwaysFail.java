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
 * Assertion that will always fail.
 *
 * <p>Sometimes useful when you'd like to see a returned message, by causing the test to fail.</p>
 *
 * @service-test-config assert-always-fail
 */
@XStreamAlias("assert-always-fail")
public class AssertAlwaysFail implements Assertion {

  private Boolean showReturnedMessage;

  @Override
  public AssertionResult execute(TestMessage actual, ServiceTestConfig config) throws ServiceTestException {
    return new AssertionResult("assert-always-fail", false);
  }

  @Override
  public String expected() {
    return "This is expected... Will always fail";
  }

  /**
   * Set value to used in {@link #showReturnedMessage()}.
   * @param showReturnedMessage value to used in {@link #showReturnedMessage()}
   */
  public void setShowReturnedMessage(Boolean showReturnedMessage) {
    this.showReturnedMessage = showReturnedMessage;
  }

  /**
   * Set value to used in {@link #showReturnedMessage()}.
   * @return value to used in {@link #showReturnedMessage()}, default: true
   */
  public Boolean getShowReturnedMessage() {
    return showReturnedMessage;
  }

  @Override
  public boolean showReturnedMessage() {
    return getShowReturnedMessage() == null ? true : getShowReturnedMessage();
  }
}
