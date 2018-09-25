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

package com.adaptris.tester.runtime;

import com.adaptris.tester.report.junit.JUnitReportTestSuites;
import com.adaptris.tester.stubs.StubClient;
import junit.framework.TestCase;

import java.util.HashMap;

public class TestListTest extends TestCase {

  public void testSetUniqueId() throws Exception {
    TestList tl = new TestList();
    tl.setUniqueId("id");
    assertEquals("id", tl.getUniqueId());
  }

  public void testAddTestCase() throws Exception {
    TestList tl = new TestList();
    tl.addTest(new Test());
    assertEquals(1, tl.size());
    assertEquals(1, tl.getTests().size());
    assertTrue(tl.iterator().hasNext());
  }

  public void testExecute() throws Exception {
    TestList tl = new TestList();
    tl.setUniqueId("id");
    tl.addTest(new Test());
    JUnitReportTestSuites s = tl.execute(new StubClient(), new ServiceTestConfig());
    assertNotNull(s);
    assertEquals(1, s.getTestSuites().size());
    assertEquals("id", s.getName());
  }

}
