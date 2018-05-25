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

import com.adaptris.tester.runtime.messages.TestMessage;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

public class AssertPayloadEqualsFileTest extends AssertionCase {

  public AssertPayloadEqualsFileTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    File file = new File(this.getClass().getClassLoader().getResource("test.properties").getFile());
    Assertion matcher = new AssertPayloadEqualsFile("file:///" + file.getAbsolutePath());
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),"foo=bar")).isPassed());
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),"something-else")).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Payload file: file:///./message.xml", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception{
    File file = new File(this.getClass().getClassLoader().getResource("test.properties").getFile());
    AssertionResult result  = new AssertPayloadEqualsFile("file:///" + file.getAbsolutePath()).execute(new TestMessage());
    assertEquals("Assertion Failure: [assert-payload-equals-file]", result.getMessage());
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertPayloadEqualsFile("file:///./message.xml");
  }
}
