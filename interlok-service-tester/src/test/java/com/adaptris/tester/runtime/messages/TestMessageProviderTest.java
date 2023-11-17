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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.adaptris.core.CoreConstants;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.metadata.EmptyMetadataProvider;
import com.adaptris.tester.runtime.messages.metadata.InlineMetadataProvider;
import com.adaptris.tester.runtime.messages.payload.FilePayloadProvider;
import com.adaptris.tester.runtime.messages.payload.InlinePayloadProvider;
import com.adaptris.util.KeyValuePairSet;
import com.adaptris.util.text.mime.MimeConstants;

public class TestMessageProviderTest extends MessagesCase {

  private static final String BASE64_ENCODED = "SlVMSUVUICBBeSBtZSEKUk9NRU8gICBTaGUgc3BlYWtzOgogICAgICAgIE8sIHNwZWFrIGFnYWluLCBicmlnaHQgYW5nZWwhIGZvciB0aG91IGFydAogICAgICAgIEFzIGdsb3Jpb3VzIHRvIHRoaXMgbmlnaHQsIGJlaW5nIG8nZXIgbXkgaGVhZAogICAgICAgIEFzIGlzIGEgd2luZ2VkIG1lc3NlbmdlciBvZiBoZWF2ZW4KICAgICAgICBVbnRvIHRoZSB3aGl0ZS11cHR1cm5lZCB3b25kZXJpbmcgZXllcwogICAgICAgIE9mIG1vcnRhbHMgdGhhdCBmYWxsIGJhY2sgdG8gZ2F6ZSBvbiBoaW0KICAgICAgICBXaGVuIGhlIGJlc3RyaWRlcyB0aGUgbGF6eS1wYWNpbmcgY2xvdWRzCiAgICAgICAgQW5kIHNhaWxzIHVwb24gdGhlIGJvc29tIG9mIHRoZSBhaXIuCkpVTElFVCAgTyBSb21lbywgUm9tZW8hIHdoZXJlZm9yZSBhcnQgdGhvdSBSb21lbz8KICAgICAgICBEZW55IHRoeSBmYXRoZXIgYW5kIHJlZnVzZSB0aHkgbmFtZTsKICAgICAgICBPciwgaWYgdGhvdSB3aWx0IG5vdCwgYmUgYnV0IHN3b3JuIG15IGxvdmUsCiAgICAgICAgQW5kIEknbGwgbm8gbG9uZ2VyIGJlIGEgQ2FwdWxldC4KICAtLSBXaWxsaWFtIFNoYWtlc3BlYXJlLCBSb21lbyBhbmQgSnVsaWV0LCBBY3QgSUksIFNjZW5lIElJCg==";

  @Test
  public void testEmptyConstructor() throws Exception {
    TestMessageProvider p = new TestMessageProvider();
    TestMessage m = p.createTestMessage(new ServiceTestConfig());
    assertEquals("", m.getPayload());
    assertEquals(0, m.getMessageHeaders().size());
  }

  @Test
  public void testConstructor() throws Exception {
    TestMessageProvider p = new TestMessageProvider(new InlineMetadataProvider(new KeyValuePairSet(metadata)),
        new InlinePayloadProvider(PAYLOAD));
    TestMessage m = p.createTestMessage(new ServiceTestConfig());
    assertEquals(1, m.getMessageHeaders().size());
    assertTrue(m.getMessageHeaders().containsKey(METADATA_KEY));
    assertEquals(METADATA_VALUE, m.getMessageHeaders().get(METADATA_KEY));
    assertEquals(PAYLOAD, m.getPayload());
  }

  @Test
  public void testGetMetadataProvider() throws Exception {
    TestMessageProvider m = new TestMessageProvider();
    m.setMetadataProvider(new InlineMetadataProvider());
    assertTrue(m.getMetadataProvider() instanceof InlineMetadataProvider);
  }

  @Test
  public void testGetPayloadProvider() throws Exception {
    TestMessageProvider m = new TestMessageProvider();
    m.setPayloadProvider(new InlinePayloadProvider());
    assertTrue(m.getPayloadProvider() instanceof InlinePayloadProvider);
  }

  @Test
  public void testBase64FilePayloadProvider() throws Exception {
    String path = getClass().getClassLoader().getResource("base64-test.txt").getFile();
    TestMessageProvider p = new TestMessageProvider(new EmptyMetadataProvider(), new FilePayloadProvider(path));
    TestMessage m = p.createTestMessage(new ServiceTestConfig());
    assertEquals(BASE64_ENCODED, m.getPayload());
    assertEquals(MimeConstants.ENCODING_BASE64, m.getMessageHeaders().get(CoreConstants.SERIALIZED_MESSAGE_ENCODING));
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new TestMessageProvider(new InlineMetadataProvider(new KeyValuePairSet(metadata)), new InlinePayloadProvider(PAYLOAD));
  }

}
