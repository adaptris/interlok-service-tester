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

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.metadata.EmptyMetadataProvider;
import com.adaptris.tester.runtime.messages.metadata.MetadataProvider;
import com.adaptris.tester.runtime.messages.payload.EmptyPayloadProvider;
import com.adaptris.tester.runtime.messages.payload.FilePayloadProvider;
import com.adaptris.tester.runtime.messages.payload.PayloadProvider;
import com.thoughtworks.xstream.annotations.XStreamAlias;

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
    TestMessage message = new TestMessage(getMetadataProvider().getMessageHeaders(), getPayloadProvider().getPayload());
    if (getPayloadProvider() instanceof FilePayloadProvider) {
      message.addMessageHeader("_interlokMessageSerialization", "BASE64");
    }
    return message;
  }
}
