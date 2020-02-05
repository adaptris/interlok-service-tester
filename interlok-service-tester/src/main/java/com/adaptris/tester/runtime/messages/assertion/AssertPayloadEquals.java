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

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals {@link #getPayload()}
 *
 * @service-test-config assert-payload-equals
 */
@XStreamAlias("assert-payload-equals")
public class AssertPayloadEquals extends PayloadAssertion {

  public AssertPayloadEquals(){
    super();
  }

  public AssertPayloadEquals(String payload){
    super(payload);
  }

  @Override
  public AssertionResult execute(String actual) {
    return new AssertionResult(getUniqueId(), "assert-payload-equals", getPayload().equals(actual));
  }
}
