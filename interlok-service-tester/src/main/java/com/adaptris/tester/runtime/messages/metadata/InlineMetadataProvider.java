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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.adaptris.annotation.AutoPopulated;
import com.adaptris.util.KeyValuePair;
import com.adaptris.util.KeyValuePairBag;
import com.adaptris.util.KeyValuePairSet;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config inline-metadata-provider
 */
@XStreamAlias("inline-metadata-provider")
public class InlineMetadataProvider extends MetadataProvider {

  @AutoPopulated
  private KeyValuePairSet metadata;

  public InlineMetadataProvider(){
    setMetadata(new KeyValuePairSet());
  }

  public InlineMetadataProvider(final KeyValuePairSet metadata){
    setMetadata(metadata);
  }

  public void setMetadata(KeyValuePairSet metadata) {
    this.metadata = metadata;
  }

  public KeyValuePairSet getMetadata() {
    return metadata;
  }

  @Override
  public Map<String, String> getMessageHeaders() {
    return Collections.unmodifiableMap(toMap(getMetadata()));
  }

  private Map<String, String> toMap(KeyValuePairBag bag) {
    Map<String, String> result = new HashMap<>(bag.size());
    for (KeyValuePair kvp : bag) {
      result.put(kvp.getKey(), kvp.getValue());
    }
    return result;
  }
}
