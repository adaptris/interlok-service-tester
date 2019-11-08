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

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks the value of {@link TestMessage#getMessageHeaders()} contains kvp with key matching {@link #getKey()}.
 *
 * @service-test-config assert-metadata-key-exists
 */
@XStreamAlias("assert-metadata-key-exists")
public class AssertMetadataKeyExists extends AssertMetadataKeyImpl {

  private final static String TEST_ID = "assert-metadata-key-exists";

  public AssertMetadataKeyExists(){
  }

  public AssertMetadataKeyExists(String key){
    this();
    setKey(key);
  }

  @Override
  public AssertionResult execute(TestMessage actual, ServiceTestConfig config) throws ServiceTestException {
    String message = String.format("Assertion Failure: [%s] metadata does not contain key: [%s]", TEST_ID, getKey());
    return new AssertionResult(getUniqueId(), TEST_ID, actual.getMessageHeaders().containsKey(getKey()), message);
  }

  @Override
  public String expected() {
    return "Metadata contains key: [" + getKey() + "]";
  }


}
