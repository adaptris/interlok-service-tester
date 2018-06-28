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

package com.adaptris.tester.runtime.messages.metadata;

import com.adaptris.tester.runtime.messages.MessagesCase;
import com.adaptris.util.KeyValuePairSet;

public class InlineMetadataProviderTest extends MessagesCase {
  public InlineMetadataProviderTest(String name) {
    super(name);
  }

  public void testEmptyConstructor() throws Exception{
    InlineMetadataProvider m = new InlineMetadataProvider();
    assertEquals(0, m.getMessageHeaders().size());
  }

  public void testConstructor() throws Exception{
    InlineMetadataProvider m = new InlineMetadataProvider(new KeyValuePairSet(metadata));
    assertEquals(1, m.getMessageHeaders().size());
    assertTrue(m.getMessageHeaders().containsKey(METADATA_KEY));
    assertEquals(METADATA_VALUE, m.getMessageHeaders().get(METADATA_KEY));
  }

  public void testGetMetadata() throws Exception {
    InlineMetadataProvider m = new InlineMetadataProvider(new KeyValuePairSet(metadata));
    assertEquals(1, m.getMetadata().size());
    assertNotNull(m.getMetadata().getKeyValuePair(METADATA_KEY));
    assertEquals(METADATA_VALUE, m.getMetadata().getValue(METADATA_KEY));
  }

  public void testGetMessageHeaders() throws Exception {
    InlineMetadataProvider m = new InlineMetadataProvider(new KeyValuePairSet(metadata));
    assertEquals(1, m.getMessageHeaders().size());
    assertTrue(m.getMessageHeaders().containsKey(METADATA_KEY));
    assertEquals(METADATA_VALUE, m.getMessageHeaders().get(METADATA_KEY));
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new InlineMetadataProvider(new KeyValuePairSet(metadata));
  }
}
