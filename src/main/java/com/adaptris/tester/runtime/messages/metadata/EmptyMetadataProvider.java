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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @service-test-config empty-metadata-provider
 */
@XStreamAlias("empty-metadata-provider")
public class EmptyMetadataProvider extends MetadataProvider {

  @XStreamOmitField
  private Map<String, String> messageHeaders;

  public EmptyMetadataProvider(){
    this.messageHeaders = new HashMap<>();
  }

  @Override
  public Map<String, String> getMessageHeaders() {
    return messageHeaders;
  }
}
