package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.XpathCommon;
import com.adaptris.tester.runtime.XpathCommonException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

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
      final boolean xpathResult = selectSingleBoolean(actual.getPayload(), getXpath());
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
