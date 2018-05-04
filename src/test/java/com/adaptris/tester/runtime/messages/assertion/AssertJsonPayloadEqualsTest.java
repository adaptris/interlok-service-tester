package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author mwarman
 */
public class AssertJsonPayloadEqualsTest extends AssertionCase  {

  public AssertJsonPayloadEqualsTest(String name) {
    super(name);
  }

  public void testExecute() throws Exception{
    String actual = "{ \"key\" : \"value\" }";
    PayloadAssertion matcher = new AssertJsonPayloadEquals();
    matcher.setPayload("{ \"key\" : \"value\" }");
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),actual)).isPassed());
    matcher.setPayload("{ \"key\" : \"value\",  \"key1\" : \"value2\"}");
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),actual)).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Payload: { \"key\" : \"value\" }", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception{
    AssertionResult result  = createAssertion().execute(new TestMessage(new HashMap<String, String>(), "{ \"key\" : \"value\",  \"key1\" : \"value2\"}"));
    assertTrue(result.getMessage().startsWith("Assertion Failure: [assert-json-payload-equals]"));
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertJsonPayloadEquals("{ \"key\" : \"value\" }");
  }
}