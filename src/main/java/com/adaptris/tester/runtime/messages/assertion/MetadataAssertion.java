package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.util.KeyValuePair;
import com.adaptris.util.KeyValuePairBag;
import com.adaptris.util.KeyValuePairSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract implementations for assertions that work on metadata.
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 */
public abstract class MetadataAssertion implements Assertion {

  private String uniqueId;
  private KeyValuePairSet metadata;

  public MetadataAssertion(){
    setMetadata(new KeyValuePairSet());
  }

  public MetadataAssertion(KeyValuePairSet metadata){
    setMetadata(metadata);
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
   * Set the expected metadata kvps to be used during assertions.
   * @param metadata the expected metadata kvps
   */
  public void setMetadata(KeyValuePairSet metadata) {
    this.metadata = metadata;
  }

  /**
   * Get the metadata kvps to be used during assertions.
   * @return the metadata kvps
   */
  public KeyValuePairSet getMetadata() {
    return metadata;
  }

  /**
   * Helper method to return {@link #getMetadata()} as {@link Map}.
   * @return the metadata kvps as map.
   */
  protected Map<String, String> getMessageHeaders() {
    return Collections.unmodifiableMap(toMap(metadata));
  }

  /**
   * Execute assertion against metadata values.
   * @param actual Metadata
   * @return Return result of assertion using {@link AssertionResult}
   */
  protected abstract AssertionResult execute(Map<String, String> actual);

  /**
   * Executes {@link #execute(Map)} with result of {@link TestMessage#getMessageHeaders()}.
   * @param actual Message resulting from text execution
   * @return Return result of assertion using {@link AssertionResult}
   */
  @Override
  public final AssertionResult execute(TestMessage actual){
    return execute(actual.getMessageHeaders());
  }

  private Map<String, String> toMap(KeyValuePairBag bag) {
    Map<String, String> result = new HashMap<>(bag.size());
    for (KeyValuePair kvp : bag) {
      result.put(kvp.getKey(), kvp.getValue());
    }
    return result;
  }

  @Override
  public String expected() {
    return "Metadata: " + getMessageHeaders();
  }

  @Override
  public boolean showReturnedMessage() {
    return true;
  }
}
