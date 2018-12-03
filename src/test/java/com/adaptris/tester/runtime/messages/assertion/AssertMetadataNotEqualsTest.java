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
import com.adaptris.util.KeyValuePairSet;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AssertMetadataNotEqualsTest extends AssertionCase {

  public AssertMetadataNotEqualsTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    Map<String, String> expected = new HashMap<>();
    expected.put("key1", "val1");
    Map<String, String> actual = new HashMap<>();
    actual.put("key1", "val1");
    MetadataAssertion matcher = new AssertMetadataNotEquals();
    KeyValuePairSet kvp = new KeyValuePairSet(expected);
    matcher.setMetadata(kvp);
    assertFalse(matcher.execute(new TestMessage(actual,""), new ServiceTestConfig()).isPassed());
    actual.put("key1", "other");
    assertTrue(matcher.execute(new TestMessage(actual,""), new ServiceTestConfig()).isPassed());
    actual.remove("key1");
    actual.put("key2", "val2");
    assertTrue(matcher.execute(new TestMessage(actual,""), new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected(){
    assertEquals("Metadata: {key1=val1}", createAssertion().expected());
  }

  @Test
  public void testGetMessage() throws Exception {
    AssertionResult result  = createAssertion().execute(new TestMessage(), new ServiceTestConfig());
    assertEquals("Assertion Failure: [assert-metadata-not-equals]", result.getMessage());
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    Map<String, String> expected = new HashMap<>();
    expected.put("key1", "val1");
    return new AssertMetadataNotEquals(expected);
  }
}
