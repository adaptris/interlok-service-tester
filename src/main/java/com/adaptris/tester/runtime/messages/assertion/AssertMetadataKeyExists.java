package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks the value of {@link TestMessage#getMessageHeaders()} contains kvp with key matching {@link #getKey()}.
 *
 * @service-test-config assert-metadata-key-exists
 */
@XStreamAlias("assert-metadata-key-exists")
public class AssertMetadataKeyExists implements Assertion {

  private final static String TEST_ID = "assert-metadata-key-exists";

  private String uniqueId;
  private String key;

  public AssertMetadataKeyExists(){
  }

  public AssertMetadataKeyExists(String key){
    setKey(key);
  }

  @Override
  public AssertionResult execute(TestMessage actual) throws ServiceTestException {
    String message = String.format("Assertion Failure: [%s] metadata does not contain key: [%s]", TEST_ID, getKey());
    return new AssertionResult(getUniqueId(), TEST_ID, actual.getMessageHeaders().containsKey(getKey()), message);
  }

  @Override
  public String expected() {
    return  "Metadata contain key: [" + key + "]";
  }

  @Override
  public boolean showReturnedMessage() {
    return true;
  }

  @Override
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
