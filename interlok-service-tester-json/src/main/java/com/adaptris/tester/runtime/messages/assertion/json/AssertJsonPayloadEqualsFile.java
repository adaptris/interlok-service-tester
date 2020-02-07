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

package com.adaptris.tester.runtime.messages.assertion.json;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.messages.assertion.AssertPayloadEqualsFile;
import com.adaptris.tester.runtime.messages.assertion.AssertionResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Checks if {@link TestMessage#getPayload()} equals file contents.
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 *
 * @service-test-config assert-json-payload-equals-file
 */
@XStreamAlias("assert-json-payload-equals-file")
public class AssertJsonPayloadEqualsFile extends AssertPayloadEqualsFile {


  public AssertJsonPayloadEqualsFile(){
  }

  public AssertJsonPayloadEqualsFile(String file){
    setFile(file);
  }

  @Override
  public AssertionResult checkResults(String actual, String expected) throws ServiceTestException{
    try {
      JSONCompareResult result = JSONCompare.compareJSON(expected, actual, JSONCompareMode.STRICT);
      return new AssertionResult("assert-json-payload-equals-file", !result.failed(),
          "Assertion Failure: [assert-json-payload-equals-file]\n" + result.getMessage());
    } catch (JSONException e) {
      throw new ServiceTestException(e);
    }
  }
}
