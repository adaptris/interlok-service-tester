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

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.adaptris.tester.runtime.messages.MessagesCase;

public class EmptyPayloadProviderTest extends MessagesCase {

  @Test
  public void getMessageHeaders() throws Exception {
    EmptyPayloadProvider m = new EmptyPayloadProvider();
    assertEquals("", m.getPayload());
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new EmptyPayloadProvider();
  }
}
