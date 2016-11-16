package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.util.KeyValuePairSet;

import java.util.HashMap;
import java.util.Map;

public class AssertXpathEqualsTest extends AssertionCase {

  private final static String PAYLOAD = "<root><key>value</key></root>";

  public AssertXpathEqualsTest(String name) {
    super(name);
  }

  public void testGetUniqueId() throws Exception {
    AssertXpathEquals a = createAssertion();
    assertEquals("id", a.getUniqueId());
  }

  public void testGetValue() throws Exception {
    AssertXpathEquals a =  createAssertion();
    a.setValue("value");
    assertEquals("value", a.getValue());
  }

  public void testExecute() throws Exception {
    AssertXpathEquals a  = createAssertion();
    assertTrue(a.execute(new TestMessage(new HashMap<String, String>(),PAYLOAD)).isPassed());
  }

  public void testExpected(){
    assertEquals("Value [value] at Xpath [/root/key/text()]", createAssertion().expected());
  }

  public void testGetMessage() throws Exception {
    AssertionResult result  = createAssertion().execute(new TestMessage(new HashMap<String, String>(), PAYLOAD));
    assertEquals("Assertion Failure: [assert-xpath-equals]", result.getMessage());
  }

  @Override
  protected AssertXpathEquals createAssertion() {
    AssertXpathEquals a= new AssertXpathEquals();
    a.setUniqueId("id");
    a.setValue("value");
    a.setXpath("/root/key/text()");
    Map<String, String> namespace = new HashMap<>();
    namespace.put("xhtml", "http://www.w3.org/1999/xhtml");
    a.setNamespaceContext(new KeyValuePairSet(namespace));
    return a;
  }
}