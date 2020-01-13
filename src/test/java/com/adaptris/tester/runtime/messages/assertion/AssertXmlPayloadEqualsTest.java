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

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class AssertXmlPayloadEqualsTest extends AssertionCase {

  @Test
  public void testExecute() throws Exception {
    String actual = "<xml />";
    PayloadAssertion matcher = new AssertPayloadEquals();
    matcher.setPayload("<xml />");
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),actual), new ServiceTestConfig()).isPassed());
    matcher.setPayload("<xml att=\"1\" />");
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),actual), new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Payload: <xml />", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception{
    AssertionResult result  = createAssertion().execute(new TestMessage(new HashMap<String, String>(), "<xml att=\"1\" />"), new ServiceTestConfig());
    assertTrue(result.getMessage().startsWith("Assertion Failure: [assert-xml-payload-equals]"));
  }

  @Test
  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertXmlPayloadEquals( "<xml />");
  }

  @Override
  public boolean isAnnotatedForJunit4() {
    return true;
  }
}
