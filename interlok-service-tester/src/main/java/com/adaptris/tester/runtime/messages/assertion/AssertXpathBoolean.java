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
import com.adaptris.tester.runtime.XpathCommon;
import com.adaptris.tester.runtime.XpathCommonException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks boolean result of {@link #getXpath()} against {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()}.
 *
 * <p><b>Example:</b></p>
 * <p>Payload:<br />
 * {@code <root><key>value</key></root> }
 * </p>
 * <p>Xpath:<br />
 * {@code count(/root/key) = 1}
 * </p>
 *
 * @service-test-config assert-xpath-boolean
 */
@XStreamAlias("assert-xpath-boolean")
public class AssertXpathBoolean extends XpathCommon implements Assertion {

  @Deprecated
  private String uniqueId;

  @Override
  @Deprecated
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public AssertionResult execute(TestMessage actual, ServiceTestConfig config) throws ServiceTestException {
    try {
      final String type = "assert-xpath-boolean";
      final boolean xpathResult = selectSingleBoolean(actual.getPayload());
      String message = String.format("Assertion Failure: [%s] Expected [%s] Returned [%s]", type, "true", xpathResult);
      return new AssertionResult(type, xpathResult, message);
    } catch (XpathCommonException e) {
      throw new ServiceTestException(e);
    }
  }

  @Override
  public String expected() {
    return "Xpath [" + getXpath() + "] did not return true";
  }

  @Override
  public boolean showReturnedMessage() {
    return true;
  }

}
