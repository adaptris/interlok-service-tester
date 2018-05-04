package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.annotation.MarshallingCDATA;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;

/**
 * Abstract implementations for assertions that work on payload.
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 */
public abstract class PayloadAssertion implements Assertion {

  private String uniqueId;
  @MarshallingCDATA
  private String payload;

  public PayloadAssertion(){
    setPayload("");
  }

  public PayloadAssertion(String payload){
    setPayload(payload);
  }

  @Override
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
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
  public final AssertionResult execute(TestMessage actual) throws ServiceTestException {
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
