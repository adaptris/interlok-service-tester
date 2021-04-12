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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;

public class AssertNextServiceIdTest extends AssertionCase {

  @Test
  public void testExecute() throws Exception {
    Assertion a = createAssertion();
    TestMessage tm = new TestMessage();
    tm.setNextServiceId("next-service");
    assertTrue(a.execute(tm, new ServiceTestConfig()).isPassed());
    tm.setNextServiceId("not-this-service");
    assertFalse(a.execute(tm, new ServiceTestConfig()).isPassed());
  }

  @Test
  public void testExpected() throws Exception {
    Assertion a = createAssertion();
    assertEquals("Next service id: next-service", a.expected());
  }

  @Test
  public void testGetValue(){
    AssertNextServiceId a = createAssertion();
    assertEquals("next-service", a.getValue());
  }

  @Test
  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected AssertNextServiceId createAssertion() {
    AssertNextServiceId a = new AssertNextServiceId();
    a.setValue("next-service");
    return a;
  }
}
