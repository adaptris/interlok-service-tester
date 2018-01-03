package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.XpathCommon;
import com.adaptris.tester.runtime.XpathCommonException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks boolean result of {@link #getXpath()} against {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()}
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

  private String uniqueId;

  @Override
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  @Override
  public AssertionResult execute(TestMessage actual) throws ServiceTestException {
    try {
      final String type = "assert-xpath-boolean";
      final boolean xpathResult = selectSingleBoolean(actual.getPayload());
      String message = String.format("Assertion Failure: [%s] Expected [%s] Returned [%s]", type, "true", xpathResult);
      return new AssertionResult(getUniqueId(), type, xpathResult, message);
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
