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

import com.adaptris.core.SerializableAdaptrisMessage;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class MessageTranslatorTest extends TestCase {

  protected static final String METADATA_KEY = "key";
  protected static final String METADATA_VALUE = "value";
  protected static final String PAYLOAD = "payload";
  private static final String NEXT_SERVICE_ID = "nextServiceId";
  protected Map<String, String> metadata;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    metadata = new HashMap<>();
    metadata.put(METADATA_KEY, METADATA_VALUE);
  }

  public void testTranslateAdaptrisMessage() throws Exception {
    MessageTranslator t = new MessageTranslator();
    SerializableAdaptrisMessage sm = new SerializableAdaptrisMessage();
    sm.setMessageHeaders(metadata);
    sm.setPayload(PAYLOAD, "UTF-8");
    sm.setNextServiceId(NEXT_SERVICE_ID);
    TestMessage m = t.translate(sm);
    assertEquals(PAYLOAD, m.getPayload());
    assertEquals(1, m.getMessageHeaders().size());
    assertTrue(m.getMessageHeaders().containsKey(METADATA_KEY));
    assertEquals(METADATA_VALUE, m.getMessageHeaders().get(METADATA_KEY));
    assertEquals(NEXT_SERVICE_ID, m.getNextServiceId());
  }

  public void testTranslateTestMessage() throws Exception {
    MessageTranslator t = new MessageTranslator();
    SerializableAdaptrisMessage sm = t.translate(new TestMessage(metadata, PAYLOAD));
    assertEquals(PAYLOAD, sm.getContent());
    assertEquals(1, sm.getMessageHeaders().size());
    assertTrue(sm.getMessageHeaders().containsKey(METADATA_KEY));
    assertEquals(METADATA_VALUE, sm.getMessageHeaders().get(METADATA_KEY));
  }

}
