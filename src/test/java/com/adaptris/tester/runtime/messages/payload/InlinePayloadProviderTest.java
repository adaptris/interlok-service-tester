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

import com.adaptris.tester.runtime.messages.MessagesCase;

public class InlinePayloadProviderTest extends MessagesCase {

  public InlinePayloadProviderTest(String name) {
    super(name);
  }

  public void testEmptyConstructor() throws Exception{
    InlinePayloadProvider m = new InlinePayloadProvider();
    assertEquals("", m.getPayload());
  }

  public void testConstructor() throws Exception{
    InlinePayloadProvider m = new InlinePayloadProvider(PAYLOAD);
    assertEquals(PAYLOAD, m.getPayload());
  }

  public void testGetPayload() throws Exception {
    InlinePayloadProvider m = new InlinePayloadProvider(PAYLOAD);
    assertEquals(PAYLOAD, m.getPayload());
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new InlinePayloadProvider(PAYLOAD);
  }
}
