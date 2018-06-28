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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks if all keys and corresponding regular expression in values set in {@link #getMessageHeaders()} match
 * {@link com.adaptris.tester.runtime.messages.TestMessage#getMessageHeaders()}.
 *
 * @service-test-config assert-metadata-matches-regex
 */
@XStreamAlias("assert-metadata-matches-regex")
public class AssertMetadataMatchesRegex extends MetadataAssertion {

  public AssertMetadataMatchesRegex(){
    super();
  }

  public AssertMetadataMatchesRegex(Map<String, String> metadata){
    super(new KeyValuePairSet(metadata));
  }

  @Override
  public AssertionResult execute(Map<String, String> actual) {
    String testType = "assert-metadata-matches-regex";
    for(Map.Entry<String, String> entry :  getMessageHeaders().entrySet()){
      if(actual.containsKey(entry.getKey())){
        String actualValue = actual.get(entry.getKey());
        String expectedRegex = entry.getValue();
        Pattern pattern = Pattern.compile(expectedRegex);
        Matcher m = pattern.matcher(actualValue);
        if (!m.find()) {
          String message = String.format("Assertion Failure: [%s] metadata contains [%s] but does not match [%s]", testType, entry.getKey(), entry.getValue());
          return new AssertionResult(getUniqueId(), testType, false, message);
        }
      } else {
        String message = String.format("Assertion Failure: [%s] message doesn't contain: [%s]", testType, entry.getKey());
        return new AssertionResult(getUniqueId(), testType, false, message);
      }
    }
    return new AssertionResult(getUniqueId(), testType, true);
  }
}
