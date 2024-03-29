/*
    Copyright 2018 Adaptris Ltd.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.adaptris.tester.runtime.messages.assertion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.util.KeyValuePairSet;

public class AssertXpathEqualsTest extends AssertionCase {

  private final static String PAYLOAD = "<root><key attribute=\"att\">value</key></root>";

  @Test
  public void testGetValue() throws Exception {
    AssertXpathEquals a =  createAssertion();
    a.setValue("value");
    assertEquals("value", a.getValue());
  }

  @Test
  public void testExecute() throws Exception {
    AssertXpathEquals a  = createAssertion();
    assertTrue(a.execute(new TestMessage(new HashMap<String, String>(),PAYLOAD), new ServiceTestConfig()).isPassed());
  }
 

  @Test
  public void testExpected(){
    assertEquals("Value [value] at Xpath [/root/key/text()]", createAssertion().expected());
  }
  
  @Test
  public void testGetMessage() throws Exception {
    AssertionResult result  = createAssertion().execute(new TestMessage(new HashMap<String, String>(), PAYLOAD), new ServiceTestConfig());
    assertEquals("Assertion Failure: [assert-xpath-equals] Expected [value] Returned [value]", result.getMessage());
  }

  @Test
  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }
  
  @Test
  public void testAttributeExpected(){
    assertEquals("Value [attribute=\"att\"] at Xpath [/root/key/@attribute]", attributeAssertion().expected());
  }

  @Test
  public void testAttributeExecute() throws Exception {
    AssertXpathEquals a  = attributeAssertion();
    assertTrue(a.execute(new TestMessage(new HashMap<String, String>(),PAYLOAD), new ServiceTestConfig()).isPassed());
  }
  
  @Test
  public void testShowAttributeReturnedMessage(){
    assertTrue(attributeAssertion().showReturnedMessage());
  }
  
  protected AssertXpathEquals attributeAssertion() {
    AssertXpathEquals a= new AssertXpathEquals();
    a.setValue("attribute=\"att\"");
    a.setXpath("/root/key/@attribute");
    Map<String, String> namespace = new HashMap<>();
    namespace.put("xhtml", "http://www.w3.org/1999/xhtml");
    a.setNamespaceContext(new KeyValuePairSet(namespace));
    return a;
  }

  @Override
  protected AssertXpathEquals createAssertion() {
    AssertXpathEquals a= new AssertXpathEquals();
    a.setValue("value");
    a.setXpath("/root/key/text()");
    Map<String, String> namespace = new HashMap<>();
    namespace.put("xhtml", "http://www.w3.org/1999/xhtml");
    a.setNamespaceContext(new KeyValuePairSet(namespace));
    return a;
  }
}
