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
import com.adaptris.tester.runtime.messages.TestMessage;

public class AssertNextServiceIdTest extends AssertionCase {

  public AssertNextServiceIdTest(String name) {
    super(name);
  }

  public void testExecute() throws Exception {
    Assertion a = createAssertion();
    TestMessage tm = new TestMessage();
    tm.setNextServiceId("next-service");
    assertTrue(a.execute(tm, new ServiceTestConfig()).isPassed());
    tm.setNextServiceId("not-this-service");
    assertFalse(a.execute(tm, new ServiceTestConfig()).isPassed());
  }

  public void testExpected() throws Exception {
    Assertion a = createAssertion();
    assertEquals("Next service id: next-service", a.expected());
  }

  public void testGetUniqueId() throws Exception {
    Assertion a = createAssertion();
    assertEquals("id", a.getUniqueId());
  }

  public void testGetValue(){
    AssertNextServiceId a = createAssertion();
    assertEquals("next-service", a.getValue());
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected AssertNextServiceId createAssertion() {
    AssertNextServiceId a = new AssertNextServiceId();
    a.setUniqueId("id");
    a.setValue("next-service");
    return a;
  }

}
