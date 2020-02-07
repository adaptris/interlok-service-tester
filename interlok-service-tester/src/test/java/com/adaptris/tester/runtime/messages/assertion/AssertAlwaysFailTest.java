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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;

@SuppressWarnings("deprecation")
public class AssertAlwaysFailTest extends AssertionCase {

  @Test
  public void testExecute() throws Exception {
    Assertion a = createAssertion();
    AssertionResult result = a.execute(new TestMessage(), new ServiceTestConfig());
    assertFalse(result.isPassed());
  }

  @Test
  public void testExpected() throws Exception {
    Assertion a = createAssertion();
    assertEquals("This is expected... Will always fail", a.expected());
  }

  @Test
  public void testGetUniqueId() throws Exception {
    Assertion a = createAssertion();
    assertNull(a.getUniqueId());
  }

  @Test
  public void testShowReturnedMessage(){
    AssertAlwaysFail a = createAssertion();
    a.setShowReturnedMessage(null);
    assertTrue(a.showReturnedMessage());
    a.setShowReturnedMessage(true);
    assertTrue(a.showReturnedMessage());
    a.setShowReturnedMessage(false);
    assertFalse(a.showReturnedMessage());

  }

  @Override
  protected AssertAlwaysFail createAssertion() {
    AssertAlwaysFail a = new AssertAlwaysFail();
    a.setUniqueId("id");
    a.setShowReturnedMessage(true);
    return a;
  }
}
