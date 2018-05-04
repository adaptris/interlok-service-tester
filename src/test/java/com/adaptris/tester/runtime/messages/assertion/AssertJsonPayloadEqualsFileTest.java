package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

public class AssertJsonPayloadEqualsFileTest extends AssertionCase {

  public AssertJsonPayloadEqualsFileTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    File file = new File(this.getClass().getClassLoader().getResource("test.json").getFile());
    Assertion matcher = new AssertJsonPayloadEqualsFile("file:///" + file.getAbsolutePath());
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),"{ \"key\" : \"value\" }")).isPassed());
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),"{ \"key\" : \"value\",  \"key1\" : \"value2\"}")).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Payload file: file:///./message.json", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception{
    File file = new File(this.getClass().getClassLoader().getResource("test.json").getFile());
    AssertionResult result  = new AssertJsonPayloadEqualsFile("file:///" + file.getAbsolutePath()).execute(new TestMessage(new HashMap<String, String>(),"{ \"key\" : \"value\",  \"key1\" : \"value2\"}"));
    assertTrue(result.getMessage().startsWith("Assertion Failure: [assert-json-payload-equals-file]"));
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertJsonPayloadEqualsFile("file:///./message.json");
  }
}