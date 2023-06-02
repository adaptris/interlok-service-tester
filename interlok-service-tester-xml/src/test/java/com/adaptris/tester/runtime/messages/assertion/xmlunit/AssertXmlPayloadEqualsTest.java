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

package com.adaptris.tester.runtime.messages.assertion.xmlunit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.adaptris.interlok.junit.scaffolding.ExampleConfigGenerator;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.messages.assertion.AssertPayloadEquals;
import com.adaptris.tester.runtime.messages.assertion.Assertion;
import com.adaptris.tester.runtime.messages.assertion.AssertionResult;
import com.adaptris.tester.runtime.messages.assertion.PayloadAssertion;

public class AssertXmlPayloadEqualsTest extends ExampleConfigGenerator {

  public static final String BASE_DIR_KEY = "AssertionCase.baseDir";

  public AssertXmlPayloadEqualsTest() {
    if (PROPERTIES.getProperty(BASE_DIR_KEY) != null) {
      setBaseDir(PROPERTIES.getProperty(BASE_DIR_KEY));
    }
  }

  @Override
  protected String createExampleXml(Object object) throws Exception {
    String result = getExampleCommentHeader(object);
    result = result + configMarshaller.marshal(object);
    return result;
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return createAssertion();
  }

  @Test
  public void testExecute() throws Exception {
    String actual = "<xml />";
    PayloadAssertion matcher = new AssertPayloadEquals();
    matcher.setPayload("<xml />");
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(), actual), new ServiceTestConfig()).isPassed());
    matcher.setPayload("<xml att=\"1\" />");
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(), actual), new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected() {
    assertEquals("Payload: <xml />", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception {
    AssertionResult result = createAssertion().execute(new TestMessage(new HashMap<String, String>(), "<xml att=\"1\" />"),
        new ServiceTestConfig());
    assertTrue(result.getMessage().startsWith("Assertion Failure: [assert-xml-payload-equals]"));
  }

  @Test
  public void testShowReturnedMessage() {
    assertTrue(createAssertion().showReturnedMessage());
  }

  protected Assertion createAssertion() {
    return new AssertXmlPayloadEquals("<xml />");
  }

}
