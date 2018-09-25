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
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),"{ \"key\" : \"value\" }"), new ServiceTestConfig()).isPassed());
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),"{ \"key\" : \"value\",  \"key1\" : \"value2\"}"), new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Payload file: file:///./message.json", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception{
    File file = new File(this.getClass().getClassLoader().getResource("test.json").getFile());
    AssertionResult result  = new AssertJsonPayloadEqualsFile("file:///" + file.getAbsolutePath()).execute(new TestMessage(new HashMap<String, String>(),"{ \"key\" : \"value\",  \"key1\" : \"value2\"}"), new ServiceTestConfig());
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
