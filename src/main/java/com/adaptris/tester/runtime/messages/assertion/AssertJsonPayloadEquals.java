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

import com.adaptris.tester.runtime.ServiceTestException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

/**
 * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals {@link #getPayload()}
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 *
 * @service-test-config assert-json-payload-equals
 */
@XStreamAlias("assert-json-payload-equals")
public class AssertJsonPayloadEquals extends PayloadAssertion {

  public AssertJsonPayloadEquals(){
    super();
  }

  public AssertJsonPayloadEquals(String payload){
    super(payload);
  }

  @Override
  public AssertionResult execute(String actual) throws ServiceTestException {
    try {
      JSONCompareResult result = JSONCompare.compareJSON(getPayload(), actual, JSONCompareMode.STRICT_ORDER);
      return new AssertionResult(getUniqueId(), "assert-json-payload-equals", !result.failed(),
          "Assertion Failure: [assert-json-payload-equals]\n" + result.getMessage());
    } catch (JSONException e) {
      throw new ServiceTestException(e);
    }
  }
}
