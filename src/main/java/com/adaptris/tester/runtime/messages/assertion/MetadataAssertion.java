package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.util.KeyValuePair;
import com.adaptris.util.KeyValuePairBag;
import com.adaptris.util.KeyValuePairSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

  public void setMetadata(KeyValuePairSet metadata) {
    this.metadata = metadata;
  }

  public KeyValuePairSet getMetadata() {
    return metadata;
  }

  protected Map<String, String> getMessageHeaders() {
    return Collections.unmodifiableMap(toMap(metadata));
  }

  protected abstract AssertionResult execute(Map<String, String> actual);

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
