package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("assert-always-fail")
public class AssertAlwaysFail implements Assertion {

  private String uniqueId;
  private Boolean showReturnedMessage;


  @Override
  public AssertionResult execute(TestMessage actual) throws ServiceTestException {
    return new AssertionResult(getUniqueId(), "assert-always-fail", false);
  }

  @Override
  public String expected() {
    return "This is expected... Will always fail";
  }

  @Override
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return this.uniqueId;
  }

  public void setShowReturnedMessage(Boolean showReturnedMessage) {
    this.showReturnedMessage = showReturnedMessage;
  }

  public Boolean getShowReturnedMessage() {
    return showReturnedMessage;
  }

  @Override
  public boolean showReturnedMessage() {
    return getShowReturnedMessage() == null ? true : getShowReturnedMessage();
  }
}
