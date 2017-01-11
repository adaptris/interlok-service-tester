package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.util.KeyValuePairSet;

import java.util.HashMap;
import java.util.Map;

public class AssertXpathBooleanTest extends AssertionCase {

  private final static String PAYLOAD = "<root><key>value</key></root>";

  public AssertXpathBooleanTest(String name) {
    super(name);
  }

  public void testGetUniqueId() throws Exception {
    AssertXpathBoolean a = createAssertion();
    assertEquals("id", a.getUniqueId());
  }

  public void testExecute() throws Exception {
    AssertXpathBoolean a  = createAssertion();
    assertTrue(a.execute(new TestMessage(new HashMap<String, String>(),PAYLOAD)).isPassed());
  }

  public void testExpected(){
    assertEquals("Xpath [count(/root/key) = 1] did not return true", createAssertion().expected());
  }

  public void testGetMessage() throws Exception {
    AssertionResult result  = createAssertion().execute(new TestMessage(new HashMap<String, String>(), PAYLOAD));
    assertEquals("Assertion Failure: [assert-xpath-boolean] Expected [true] Returned [true]", result.getMessage());
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected AssertXpathBoolean createAssertion() {
    AssertXpathBoolean a = new AssertXpathBoolean();
    a.setUniqueId("id");
    a.setXpath("count(/root/key) = 1");
    Map<String, String> namespace = new HashMap<>();
    namespace.put("xhtml", "http://www.w3.org/1999/xhtml");
    a.setNamespaceContext(new KeyValuePairSet(namespace));
    return a;
  }
}