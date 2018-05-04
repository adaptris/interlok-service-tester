package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

public class AssertPayloadEqualsFileTest extends AssertionCase {

  public AssertPayloadEqualsFileTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    File file = new File(this.getClass().getClassLoader().getResource("test.properties").getFile());
    Assertion matcher = new AssertPayloadEqualsFile("file:///" + file.getAbsolutePath());
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),"foo=bar")).isPassed());
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),"something-else")).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Payload file: file:///./message.xml", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception{
    File file = new File(this.getClass().getClassLoader().getResource("test.properties").getFile());
    AssertionResult result  = new AssertPayloadEqualsFile("file:///" + file.getAbsolutePath()).execute(new TestMessage());
    assertEquals("Assertion Failure: [assert-payload-equals-file]", result.getMessage());
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertPayloadEqualsFile("file:///./message.xml");
  }
}