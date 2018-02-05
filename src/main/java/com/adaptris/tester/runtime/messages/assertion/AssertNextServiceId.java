package com.adaptris.tester.runtime.messages.assertion;

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

  private String uniqueId;
  private String value;

  @Override
  public AssertionResult execute(TestMessage actual) throws ServiceTestException {
    return new AssertionResult(getUniqueId(), "assert-next-service-id", actual.getNextServiceId().equals(getValue()));
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

  @Override
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return this.uniqueId;
  }
}
