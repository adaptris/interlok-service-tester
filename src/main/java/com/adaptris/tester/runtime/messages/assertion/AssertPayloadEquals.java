package com.adaptris.tester.runtime.messages.assertion;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals {@link #getPayload()}
 *
 * @service-test-config assert-payload-equals
 */
@XStreamAlias("assert-payload-equals")
public class AssertPayloadEquals extends PayloadAssertion {

  public AssertPayloadEquals(){
    super();
  }

  public AssertPayloadEquals(String payload){
    super(payload);
  }

  @Override
  public AssertionResult execute(String actual) {
    return new AssertionResult(getUniqueId(), "assert-payload-equals", getPayload().equals(actual));
  }
}
