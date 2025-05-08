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

package com.adaptris.tester.runtime.messages.payload;

import com.adaptris.core.*;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.nio.charset.Charset;

/**
 *
 * @service-test-config multi-payload-provider
 */
@XStreamAlias("multi-payload-provider")
public class MultiPayloadProvider extends PayloadProvider {

  private MultiPayload multiPayload = new MultiPayload();

  public MultiPayloadProvider() {}

  public MultiPayloadProvider(MultiPayload payloads) {
    this.multiPayload = payloads;
  }

  public MultiPayload getMultiPayload() {
    return multiPayload;
  }

  public void setMultiPayload(MultiPayload payloads) {
    this.multiPayload = payloads;
  }

  @Override
  public String getPayload() {
    return this.multiPayload != null ? this.multiPayload.get(MultiPayloadAdaptrisMessage.DEFAULT_PAYLOAD_ID).getPayloadAsString() : null;
  }

  public void setPayload(String payload) {
    this.multiPayload.put(MultiPayloadAdaptrisMessage.DEFAULT_PAYLOAD_ID, new MultiPayloadAdaptrisMessageImp.Payload(Charset.defaultCharset().name(), payload.getBytes()));
  }
}
