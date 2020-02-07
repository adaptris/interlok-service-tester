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

package com.adaptris.tester.runtime.messages.assertion;

import java.util.Map;
import com.adaptris.util.KeyValuePairSet;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks if {@link #getMessageHeaders()} equals {@link com.adaptris.tester.runtime.messages.TestMessage#getMessageHeaders()}
 * using {@link Map#equals(Object)}.
 *
 * @service-test-config assert-metadata-equals
 */
@XStreamAlias("assert-metadata-equals")
public class AssertMetadataEquals extends MetadataAssertion {

  public AssertMetadataEquals(){
    super();
  }

  public AssertMetadataEquals(Map<String, String> metadata){
    super(new KeyValuePairSet(metadata));
  }

  @Override
  public AssertionResult execute(Map<String, String> actual) {
    return new AssertionResult("assert-metadata-equals", getMessageHeaders().equals(actual));
  }
}
