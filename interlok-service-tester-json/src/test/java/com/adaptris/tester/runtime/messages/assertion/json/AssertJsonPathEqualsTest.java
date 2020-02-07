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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.HashMap;
import org.junit.Test;
import com.adaptris.core.ExampleConfigCase;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.messages.assertion.Assertion;
import com.adaptris.tester.runtime.messages.assertion.AssertionResult;

@SuppressWarnings("deprecation")
public class AssertJsonPathEqualsTest extends ExampleConfigCase {

  private static final String JSON_PATH = "$.store.bicycle.color";

  public AssertJsonPathEqualsTest() {
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
    Assertion assertion = createAssertion();
    assertion.setUniqueId(null);
    return assertion;
  }

  @Test
  public void testExecute() throws Exception {
    assertTrue(
        createAssertion().execute(new TestMessage(new HashMap<String, String>(), sampleJsonContent()), new ServiceTestConfig())
            .isPassed());
    assertFalse(createAssertion().withValue("blue")
        .execute(new TestMessage(new HashMap<String, String>(), sampleJsonContent()), new ServiceTestConfig()).isPassed());

  }

  @Test(expected = ServiceTestException.class)
  public void testExecute_NotJSON() throws Exception {
    createAssertion().execute(new TestMessage(new HashMap<String, String>(), "<xml/>"), new ServiceTestConfig());
  }

  @Test
  public void testExpected(){
    assertTrue(createAssertion().expected().contains(JSON_PATH));
  }

  @Test
  public void testGetMessage() throws Exception{
    File file = new File(this.getClass().getClassLoader().getResource("test.json").getFile());
    AssertionResult result = createAssertion().withValue("blue")
        .execute(new TestMessage(new HashMap<String, String>(), sampleJsonContent()), new ServiceTestConfig());
    assertTrue(result.getMessage().contains("assert-jsonpath-equals"));
  }

  @Test
  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  protected AssertJsonPathEquals createAssertion() {
    return new AssertJsonPathEquals().withValue("red").withJsonPath(JSON_PATH);
  }

  @Override
  public boolean isAnnotatedForJunit4() {
    return true;
  }
  
  public static String sampleJsonContent() {
    return "{\"store\": {\"bicycle\": {\"color\": \"red\",\"price\": 19.95}}}";
  }
}
