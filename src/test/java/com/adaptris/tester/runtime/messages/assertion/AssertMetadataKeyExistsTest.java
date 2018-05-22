package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.util.KeyValuePairSet;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AssertMetadataKeyExistsTest extends AssertionCase{

  public AssertMetadataKeyExistsTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    assertTrue(createAssertion().execute(new TestMessage(Collections.singletonMap("key1", "value1"), "payload")).isPassed());
    assertFalse(createAssertion().execute(new TestMessage(Collections.singletonMap("key2", "value1"), "payload")).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Metadata contain key: [key1]", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception {
    AssertionResult result  = createAssertion().execute(new TestMessage());
    assertEquals("Assertion Failure: [assert-metadata-key-exists] metadata does not contain key: [key1]", result.getMessage());
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertMetadataKeyExists("key1");
  }
}