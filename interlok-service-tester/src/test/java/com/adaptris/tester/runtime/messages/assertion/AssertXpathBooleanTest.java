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

public class AssertXpathBooleanTest extends AssertionCase {

  private final static String PAYLOAD = "<root><key>value</key></root>";


  @Test
  public void testExecute() throws Exception {
    AssertXpathBoolean a  = createAssertion();
    assertTrue(a.execute(new TestMessage(new HashMap<String, String>(),PAYLOAD), new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Xpath [count(/root/key) = 1] did not return true", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception {
    AssertionResult result  = createAssertion().execute(new TestMessage(new HashMap<String, String>(), PAYLOAD), new ServiceTestConfig());
    assertEquals("Assertion Failure: [assert-xpath-boolean] Expected [true] Returned [true]", result.getMessage());
  }

  @Test
  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected AssertXpathBoolean createAssertion() {
    AssertXpathBoolean a = new AssertXpathBoolean();
    a.setXpath("count(/root/key) = 1");
    Map<String, String> namespace = new HashMap<>();
    namespace.put("xhtml", "http://www.w3.org/1999/xhtml");
    a.setNamespaceContext(new KeyValuePairSet(namespace));
    return a;
  }
}
