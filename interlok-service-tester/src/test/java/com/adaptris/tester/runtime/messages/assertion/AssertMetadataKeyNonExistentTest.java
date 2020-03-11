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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Collections;
import org.junit.Test;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;

public class AssertMetadataKeyNonExistentTest extends AssertionCase{

  @Test
  public void testExecute() throws Exception {
    assertFalse(createAssertion()
        .execute(new TestMessage(Collections.singletonMap("key1", "value1"), "payload"), new ServiceTestConfig()).isPassed());
    assertTrue(createAssertion()
        .execute(new TestMessage(Collections.singletonMap("key2", "value1"), "payload"), new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Metadata does not contain key: [key1]", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception {
    AssertionResult result  = createAssertion().execute(new TestMessage(), new ServiceTestConfig());
    assertEquals("Assertion Failure: [assert-metadata-key-does-not-exist] metadata contains key: [key1]",
        result.getMessage());
  }

  @Test
  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertMetadataKeyNonExistent("key1");
  }
}
