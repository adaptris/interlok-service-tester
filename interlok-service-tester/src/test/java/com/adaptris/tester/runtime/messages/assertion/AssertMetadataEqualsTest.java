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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.util.KeyValuePairSet;

public class AssertMetadataEqualsTest extends AssertionCase{

  @Test
  public void testExecute() throws Exception {
    Map<String, String> expected = new HashMap<>();
    expected.put("key1", "val1");
    Map<String, String> actual = new HashMap<>();
    MetadataAssertion matcher = new AssertMetadataEquals();
    matcher.setMetadata(new KeyValuePairSet(expected));
    assertFalse(matcher.execute(new TestMessage(actual,""), new ServiceTestConfig()).isPassed());
    actual.put("key1", "val1");
    assertTrue(matcher.execute(new TestMessage(actual,""), new ServiceTestConfig()).isPassed());
    actual.put("key1", "valother");
    assertFalse(matcher.execute(new TestMessage(actual,""), new ServiceTestConfig()).isPassed());
    expected.remove("key1");
    expected.put("key3", "val3");
    assertFalse(matcher.execute(new TestMessage(actual,""), new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Metadata: {key1=val1}", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception {
    AssertionResult result  = createAssertion().execute(new TestMessage(), new ServiceTestConfig());
    assertEquals("Assertion Failure: [assert-metadata-equals]", result.getMessage());
  }

  @Test
  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    Map<String, String> expected = new HashMap<>();
    expected.put("key1", "val1");
    return new AssertMetadataEquals(expected);
  }
}
