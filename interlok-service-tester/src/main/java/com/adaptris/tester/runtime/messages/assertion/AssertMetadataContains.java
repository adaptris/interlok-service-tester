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

import com.adaptris.util.KeyValuePairSet;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * Checks if all keys and corresponding values set in {@link #getMessageHeaders()} are present in
 * {@link com.adaptris.tester.runtime.messages.TestMessage#getMessageHeaders()}.
 *
 * @service-test-config assert-metadata-contains
 */
@XStreamAlias("assert-metadata-contains")
public class AssertMetadataContains extends MetadataAssertion {

  public AssertMetadataContains(){
    super();
  }

  public AssertMetadataContains(Map<String, String> metadata){
    super(new KeyValuePairSet(metadata));
  }

  @Override
  public AssertionResult execute(Map<String, String> actual) {
    String testType = "assert-metadata-contains";
    for(Map.Entry<String, String> entry :  getMessageHeaders().entrySet()){
      if(!(actual.containsKey(entry.getKey()) && actual.get(entry.getKey()).equals(entry.getValue()))){
        String message = String.format("Assertion Failure: [%s] metadata does not contain kvp: {%s=%s}", testType, entry.getKey(), entry.getValue());
        return new AssertionResult(getUniqueId(), testType, false, message);
      }
    }
    return new AssertionResult(getUniqueId(), testType, true);
  }
}
