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
 * Checks if all keys and corresponding values set in {@link #getMessageHeaders()} are not present in
 * {@link com.adaptris.tester.runtime.messages.TestMessage#getMessageHeaders()}.
 *
 * @service-test-config assert-metadata-not-equals
 */
@XStreamAlias("assert-metadata-not-equals")
public class AssertMetadataNotEquals extends MetadataAssertion {

  public AssertMetadataNotEquals(){
    super();
  }

  public AssertMetadataNotEquals(Map<String, String> metadata){
    super(new KeyValuePairSet(metadata));
  }

  @Override
  public AssertionResult execute(Map<String, String> actual) {
    String testType = "assert-metadata-not-equals";
    for(Map.Entry<String, String> entry :  actual.entrySet()){
      if(getMessageHeaders().containsKey(entry.getKey()) && getMessageHeaders().get(entry.getKey()).equals(entry.getValue())){
        return new AssertionResult(testType, false);
      }
    }
    return new AssertionResult("assert-metadata-not-equals", true);
  }
}
