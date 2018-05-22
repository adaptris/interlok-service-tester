package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.util.KeyValuePairSet;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AssertMetadataMatchesRegexTest extends AssertionCase{

  public AssertMetadataMatchesRegexTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    Map<String, String> expected = new HashMap<>();
    expected.put("key1", "^val[0-9]$");
    Map<String, String> actual = new HashMap<>();
    MetadataAssertion matcher = new AssertMetadataMatchesRegex();
    matcher.setMetadata(new KeyValuePairSet(expected));
    assertFalse(matcher.execute(new TestMessage(actual,"")).isPassed());
    actual.put("key2", "val2");
    assertFalse(matcher.execute(new TestMessage(actual,"")).isPassed());
    actual.put("key1", "val1");
    assertTrue(matcher.execute(new TestMessage(actual,"")).isPassed());
    actual.put("key1", "valother");
    assertFalse(matcher.execute(new TestMessage(actual,"")).isPassed());
    actual.put("key1", "val3");
    assertTrue(matcher.execute(new TestMessage(actual,"")).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Metadata: {key1=^val[0-9]$}", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception {
    AssertionResult result  = createAssertion().execute(new TestMessage());
    assertEquals("Assertion Failure: [assert-metadata-matches-regex] message doesn't contain: [key1]", result.getMessage());
    result  = createAssertion().execute(new TestMessage(Collections.singletonMap("key1", "valother"), ""));
    assertEquals("Assertion Failure: [assert-metadata-matches-regex] metadata contains [key1] but does not match [^val[0-9]$]", result.getMessage());
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    Map<String, String> expected = new HashMap<>();
    expected.put("key1", "^val[0-9]$");
    return new AssertMetadataMatchesRegex(expected);
  }
}