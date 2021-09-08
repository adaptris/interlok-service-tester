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

import com.adaptris.core.CoreConstants;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.metadata.EmptyMetadataProvider;
import com.adaptris.tester.runtime.messages.metadata.MetadataProvider;
import com.adaptris.tester.runtime.messages.payload.EmptyPayloadProvider;
import com.adaptris.tester.runtime.messages.payload.FilePayloadProvider;
import com.adaptris.tester.runtime.messages.payload.PayloadProvider;
import com.adaptris.util.text.mime.MimeConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @service-test-config test-message-provider
 */
@XStreamAlias("test-message-provider")
public class TestMessageProvider {

  private MetadataProvider metadataProvider;

  private PayloadProvider payloadProvider;

  public TestMessageProvider(){
    setPayloadProvider(new EmptyPayloadProvider());
    setMetadataProvider(new EmptyMetadataProvider());
  }

  public TestMessageProvider(MetadataProvider metadataProvider, PayloadProvider payloadProvider){
    setMetadataProvider(metadataProvider);
    setPayloadProvider(payloadProvider);
  }


  public void setMetadataProvider(MetadataProvider metadataProvider) {
    this.metadataProvider = metadataProvider;
  }

  public MetadataProvider getMetadataProvider() {
    return metadataProvider;
  }

  public void setPayloadProvider(PayloadProvider payloadProvider) {
    this.payloadProvider = payloadProvider;
  }

  public PayloadProvider getPayloadProvider() {
    return payloadProvider;
  }

  public TestMessage createTestMessage(ServiceTestConfig config) throws MessageException {
    getMetadataProvider().init(config);
    getPayloadProvider().init(config);
    Map<String, String> headers = getMetadataProvider().getMessageHeaders();
    if (getPayloadProvider() instanceof FilePayloadProvider)
    { // The map seems to want to be unmodifiable, but we need to
      // sneakily indicate that the message is binary data that's been
      // base-64 encoded.
      headers = new HashMap<>(headers);
      headers.put(CoreConstants.SERIALIZED_MESSAGE_ENCODING, MimeConstants.ENCODING_BASE64);
      headers = Collections.unmodifiableMap(headers);
    }
    return new TestMessage(headers, getPayloadProvider().getPayload());
  }
}
