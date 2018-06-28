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

import com.adaptris.tester.report.junit.JUnitReportTestSuite;
import com.adaptris.tester.runtime.services.ServiceToTest;
import com.adaptris.tester.runtime.services.preprocessor.VarSubPropsPreprocessor;
import com.adaptris.tester.stubs.StubClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestTest extends junit.framework.TestCase {

  public void testSetUniqueId() throws Exception {
    Test t = new Test();
    t.setUniqueId("id");
    assertEquals("id", t.getUniqueId());
  }

  public void testSetServiceToTest() throws Exception {
    Test t = new Test();
    t.setServiceToTest(new ServiceToTest());
    assertNotNull(t.getServiceToTest());
  }

  public void testSetTestCases() throws Exception {
    Test t = new Test();
    t.setTestCases(Arrays.asList(new TestCase()));
    assertNotNull(t.getTestCases());
    assertEquals(1, t.getTestCases().size());
  }

  public void testAddTestCase() throws Exception {
    Test t = new Test();
    t.addTestCase(new TestCase());
    assertNotNull(t.getTestCases());
    assertEquals(1, t.getTestCases().size());
  }

  public void testExecute() throws Exception {
    Test t = new Test();
    t.addTestCase(new TestCase());
    JUnitReportTestSuite result = t.execute("testlist", new StubClient(), new HashMap<String, String>());
    assertNotNull(result.getTestCases());
    assertEquals(1, result.getTestCases().size());
    assertNotNull(result.getTime());
  }

  public void testExecuteHelperProperties() throws Exception {
    Test t = new Test();
    t.setUniqueId("id");
    t.addTestCase(new TestCase());
    Map<String, String> map = new HashMap<String, String>();
    map.put("property", "value");
    JUnitReportTestSuite result = t.execute("testlist", new StubClient(), map);
    assertEquals("testlist.id", result.getName());
    assertNotNull(result.getTestCases());
    assertEquals(1, result.getTestCases().size());
    assertNotNull(result.getTime());
    assertEquals(1, t.getServiceToTest().getPreprocessors().size());
    assertTrue(t.getServiceToTest().getPreprocessors().get(0) instanceof VarSubPropsPreprocessor);
    assertTrue(((VarSubPropsPreprocessor)t.getServiceToTest().getPreprocessors().get(0)).getKvpAsProperties().containsKey("property"));
    assertEquals("value", ((VarSubPropsPreprocessor)t.getServiceToTest().getPreprocessors().get(0)).getKvpAsProperties().get("property"));
  }

}
