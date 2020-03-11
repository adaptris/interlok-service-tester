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

package com.adaptris.tester.runtime.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class TestMessageTest {

  private static final String METADATA_KEY = "key";
  private static final String METADATA_VALUE = "value";
  private static final String PAYLOAD = "payload";

  @Test
  public void testEmptyConstructor() throws Exception{
    TestMessage m = new TestMessage();
    assertEquals("", m.getPayload());
    assertEquals(0, m.getMessageHeaders().size());
  }

  @Test
  public void testConstructor() throws Exception{
    Map<String, String> metadata = new HashMap<>();
    metadata.put(METADATA_KEY, METADATA_VALUE);
    TestMessage m = new TestMessage(metadata, PAYLOAD);
    assertEquals(PAYLOAD, m.getPayload());
    assertEquals(1, m.getMessageHeaders().size());
    assertTrue(m.getMessageHeaders().containsKey(METADATA_KEY));
    assertEquals(METADATA_VALUE, m.getMessageHeaders().get(METADATA_KEY));
  }

  @Test
  public void testSetMessageHeaders() throws Exception {
    TestMessage m = new TestMessage();
    Map<String, String> metadata = new HashMap<>();
    metadata.put(METADATA_KEY, METADATA_VALUE);
    m.setMessageHeaders(metadata);
    assertEquals(1, m.getMessageHeaders().size());
    assertTrue(m.getMessageHeaders().containsKey(METADATA_KEY));
    assertEquals(METADATA_VALUE, m.getMessageHeaders().get(METADATA_KEY));
  }

  @Test
  public void testAddMessageHeader() throws Exception {
    TestMessage m = new TestMessage();
    m.addMessageHeader(METADATA_KEY, METADATA_VALUE);
    assertEquals(1, m.getMessageHeaders().size());
    assertTrue(m.getMessageHeaders().containsKey(METADATA_KEY));
    assertEquals(METADATA_VALUE, m.getMessageHeaders().get(METADATA_KEY));
  }

  @Test
  public void testSetPayload() throws Exception {
    TestMessage m = new TestMessage();
    m.setPayload(PAYLOAD);
    assertEquals(PAYLOAD, m.getPayload());
  }

  @Test
  public void testToString() throws Exception {
    TestMessage m = new TestMessage();
    m.addMessageHeader(METADATA_KEY, METADATA_VALUE);
    m.setPayload(PAYLOAD);
    assertEquals("Metadata: {key=value}\n" +
        "Payload: payload", m.toString());
  }

}
