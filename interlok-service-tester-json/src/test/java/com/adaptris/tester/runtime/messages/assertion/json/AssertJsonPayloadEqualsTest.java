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

package com.adaptris.tester.runtime.messages.assertion.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import org.junit.Test;
import com.adaptris.core.ExampleConfigCase;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.messages.assertion.Assertion;
import com.adaptris.tester.runtime.messages.assertion.AssertionResult;
import com.adaptris.tester.runtime.messages.assertion.PayloadAssertion;
import com.adaptris.util.GuidGenerator;

/**
 * @author mwarman
 */
public class AssertJsonPayloadEqualsTest extends ExampleConfigCase {

  public AssertJsonPayloadEqualsTest() {
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
    GuidGenerator guidGenerator = new GuidGenerator();
    Assertion assertion = createAssertion();
    assertion.setUniqueId(guidGenerator.getUUID());
    return assertion;
  }


  @Test
  public void testExecute() throws Exception{
    String actual = "{ \"key\" : \"value\" }";
    PayloadAssertion matcher = new AssertJsonPayloadEquals();
    matcher.setPayload("{ \"key\" : \"value\" }");
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),actual), new ServiceTestConfig()).isPassed());
    matcher.setPayload("{ \"key\" : \"value\",  \"key1\" : \"value2\"}");
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),actual), new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Payload: { \"key\" : \"value\" }", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception{
    AssertionResult result  = createAssertion().execute(new TestMessage(new HashMap<String, String>(), "{ \"key\" : \"value\",  \"key1\" : \"value2\"}"), new ServiceTestConfig());
    assertTrue(result.getMessage().startsWith("Assertion Failure: [assert-json-payload-equals]"));
  }

  protected Assertion createAssertion() {
    return new AssertJsonPayloadEquals("{ \"key\" : \"value\" }");
  }

  @Override
  public boolean isAnnotatedForJunit4() {
    return true;
  }
}
