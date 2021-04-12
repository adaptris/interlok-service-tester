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

import com.adaptris.annotation.MarshallingCDATA;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;

/**
 * Abstract implementations for assertions that work on payload.
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 */
public abstract class PayloadAssertion implements Assertion {

  @MarshallingCDATA
  private String payload;

  public PayloadAssertion(){
    setPayload("");
  }

  public PayloadAssertion(String payload){
    setPayload(payload);
  }

  /**
   * Set the expected payload to be used during assertions.
   * @param payload the expected payload
   */
  public void setPayload(String payload) {
    this.payload = payload;
  }

  /**
   * Get the expected payload to be used during assertions.
   * @return the expected payload
   */
  public String getPayload() {
    return payload;
  }

  /**
   * Execute assertion against payload.
   * @param actual Metadata
   * @return Return result of assertion using {@link AssertionResult}
   */
  protected abstract AssertionResult execute(String actual) throws ServiceTestException;

  /**
   * Executes {@link #execute(String)} with result of {@link TestMessage#getPayload()}.
   * @param actual Message resulting from text execution
   * @return Return result of assertion using {@link AssertionResult}
   */
  @Override
  public final AssertionResult execute(TestMessage actual, ServiceTestConfig config) throws ServiceTestException {
    return execute(actual.getPayload());
  }

  @Override
  public String expected() {
    return "Payload: " + getPayload();
  }

  @Override
  public boolean showReturnedMessage() {
    return true;
  }
}
