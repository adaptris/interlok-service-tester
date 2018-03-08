package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

public class AssertXmlPayloadEqualsFileTest extends AssertionCase {

  public AssertXmlPayloadEqualsFileTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    File file = new File(this.getClass().getClassLoader().getResource("test.xml").getFile());
    Assertion matcher = new AssertXmlPayloadEqualsFile("file:///" + file.getAbsolutePath());
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),"<xml/>")).isPassed());
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),"<xml att=\"foo\" />")).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Payload file: file:///./message.xml", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception{
    File file = new File(this.getClass().getClassLoader().getResource("test.xml").getFile());
    AssertionResult result  = new AssertXmlPayloadEqualsFile("file:///" + file.getAbsolutePath()).execute(new TestMessage(new HashMap<String, String>(),"<xml att=\"foo\" />"));
    assertTrue(result.getMessage().startsWith("Assertion Failure: [assert-xml-payload-equals-file]"));
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertXmlPayloadEqualsFile("file:///./message.xml");
  }
}