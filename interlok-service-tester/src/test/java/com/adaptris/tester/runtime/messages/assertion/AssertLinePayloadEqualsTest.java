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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;

/**
 * @author mwarman
 */

public class AssertLinePayloadEqualsTest extends AssertionCase  {

  @Test
  public void testExecute() throws Exception{
    String actual = "header1,header2,header3\nvalue1,value2,value3";
    AssertLinePayloadEquals matcher = new AssertLinePayloadEquals();
    matcher.setExpectedLines(Arrays.asList("header1,header2,header3", "value1,value2,value3"));
    assertTrue(matcher.execute(new TestMessage(new HashMap<String, String>(),actual), new ServiceTestConfig()).isPassed());
    matcher.setExpectedLines(Arrays.asList("header1,header2", "value1,value2"));
    assertFalse(matcher.execute(new TestMessage(new HashMap<String, String>(),actual), new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected(){
    String result = createAssertion().expected();
    assertTrue(result.contains("header1,header2,header3"));
    assertTrue(result.contains("value1,value2,value3"));
  }

  @Test
  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected Assertion createAssertion() {
    return new AssertLinePayloadEquals(Arrays.asList("header1,header2,header3", "value1,value2,value3"));
  }

}
