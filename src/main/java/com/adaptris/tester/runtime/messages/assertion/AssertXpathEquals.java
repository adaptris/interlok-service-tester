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

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.XpathCommon;
import com.adaptris.tester.runtime.XpathCommonException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks if result of {@link #getXpath()} from {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()}
 * equals {@link #getValue()}.
 *
 * <p><b>Example:</b></p>
 * <p>Payload:<br />
 * {@code <root><key>value</key></root> }
 * </p>
 * <p>Xpath:<br />
 * {@code /root/key}
 * </p>
 * <p>Value:<br />
 * {@code value}
 * </p>
 * @service-test-config assert-xpath-equals
 */
@XStreamAlias("assert-xpath-equals")
public class AssertXpathEquals extends XpathCommon implements Assertion {

  private String uniqueId;
  private String value;

  @Override
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  /**
   * Set value to check against xpath result.
   * @param value Value to check against xpath result
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Get value to check against xpath result.
   * @return value to check against xpath result
   */
  public String getValue() {
    return value;
  }

  @Override
  public AssertionResult execute(TestMessage actual) throws ServiceTestException {
    try {
      final String type = "assert-xpath-equals";
      final String xpathResult = nodeToString(selectSingleNode(actual.getPayload()));
      String message = String.format("Assertion Failure: [%s] Expected [%s] Returned [%s]", type, getValue(), xpathResult);
      return new AssertionResult(getUniqueId(), type, getValue().equals(xpathResult), message);
    } catch (XpathCommonException e) {
      throw new ServiceTestException(e);
    }
  }

  @Override
  public String expected() {
    return "Value [" + getValue() + "] at Xpath [" + getXpath() + "]";
  }

  @Override
  public boolean showReturnedMessage() {
    return true;
  }

}
