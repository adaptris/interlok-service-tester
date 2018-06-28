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

import com.adaptris.tester.runtime.messages.metadata.InlineMetadataProvider;
import com.adaptris.tester.runtime.messages.payload.InlinePayloadProvider;
import com.adaptris.util.KeyValuePairSet;

public class TestMessageProviderTest extends MessagesCase {

  public TestMessageProviderTest(String name) {
    super(name);
  }

  public void testEmptyConstructor() throws Exception{
    TestMessageProvider p = new TestMessageProvider();
    TestMessage m = p.createTestMessage();
    assertEquals("", m.getPayload());
    assertEquals(0, m.getMessageHeaders().size());
  }

  public void testConstructor() throws Exception{
    TestMessageProvider p = new TestMessageProvider(new InlineMetadataProvider(new KeyValuePairSet(metadata)), new InlinePayloadProvider(PAYLOAD));
    TestMessage m = p.createTestMessage();
    assertEquals(1, m.getMessageHeaders().size());
    assertTrue(m.getMessageHeaders().containsKey(METADATA_KEY));
    assertEquals(METADATA_VALUE, m.getMessageHeaders().get(METADATA_KEY));
    assertEquals(PAYLOAD, m.getPayload());
  }

  public void testGetMetadataProvider() throws Exception {
    TestMessageProvider m = new TestMessageProvider();
    m.setMetadataProvider(new InlineMetadataProvider());
    assertTrue(m.getMetadataProvider() instanceof InlineMetadataProvider);
  }

  public void testGetPayloadProvider() throws Exception {
    TestMessageProvider m = new TestMessageProvider();
    m.setPayloadProvider(new InlinePayloadProvider());
    assertTrue(m.getPayloadProvider() instanceof InlinePayloadProvider);
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new TestMessageProvider(new InlineMetadataProvider(new KeyValuePairSet(metadata)), new InlinePayloadProvider(PAYLOAD));
  }
}
