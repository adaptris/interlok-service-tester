package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.annotation.MarshallingCDATA;
import com.adaptris.tester.runtime.messages.TestMessage;

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

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public String getPayload() {
    return payload;
  }

  protected abstract AssertionResult execute(String actual);

  @Override
  public final AssertionResult execute(TestMessage actual){
    return execute(actual.getPayload());
  }

  @Override
  public String expected() {
    return "Payload: " + getPayload();
  }
}
