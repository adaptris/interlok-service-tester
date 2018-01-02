package com.adaptris.tester.runtime.messages.assertion;

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
