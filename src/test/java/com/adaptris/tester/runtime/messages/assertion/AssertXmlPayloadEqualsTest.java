package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import org.junit.Test;

import java.util.HashMap;

public class AssertXmlPayloadEqualsTest extends AssertionCase {

  public AssertXmlPayloadEqualsTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    String actual = "<xml />";
    PayloadAssertion matcher = new AssertPayloadEquals();
    matcher.setPayload("<xml />");
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),actual)).isPassed());
    matcher.setPayload("<xml att=\"1\" />");
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),actual)).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Payload: <xml />", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception{
    AssertionResult result  = createAssertion().execute(new TestMessage(new HashMap<String, String>(), "<xml att=\"1\" />"));
    assertTrue(result.getMessage().startsWith("Assertion Failure: [assert-xml-payload-equals]"));
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertXmlPayloadEquals( "<xml />");
  }
}