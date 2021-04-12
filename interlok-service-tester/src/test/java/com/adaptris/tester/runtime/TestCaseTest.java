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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.adaptris.interlok.junit.scaffolding.BaseCase;
import com.adaptris.tester.report.junit.JUnitReportError;
import com.adaptris.tester.report.junit.JUnitReportFailure;
import com.adaptris.tester.report.junit.JUnitReportSkipped;
import com.adaptris.tester.report.junit.JUnitReportTestCase;
import com.adaptris.tester.report.junit.JUnitReportTestIssue;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.messages.TestMessageProvider;
import com.adaptris.tester.runtime.services.ServiceToTest;
import com.adaptris.tester.runtime.services.sources.InlineSource;
import com.adaptris.tester.stubs.StubClient;

public class TestCaseTest extends BaseCase {

  @org.junit.Test
  public void testSetUniqueId() throws Exception {
    TestCase tc = new TestCase();
    tc.setUniqueId("id");
    assertEquals("id", tc.getUniqueId());
  }

  @org.junit.Test
  public void testSetInputMessageProvider() throws Exception {
    TestCase tc = new TestCase();
    tc.setInputMessageProvider(new TestMessageProvider());
    assertNotNull(tc.getInputMessageProvider());
  }

  @org.junit.Test
  public void testSetAssertions() throws Exception {
    TestCase tc = new TestCase();
    tc.setAssertions(new Assertions());
    assertNotNull(tc.getAssertions());
  }

  @org.junit.Test
  public void testSetExpectedException() throws Exception {
    TestCase tc = new TestCase();
    tc.setExpectedException(new ExpectedException());
    assertNotNull(tc.getExpectedException());
  }

  @org.junit.Test
  public void testSetGlobFilter() throws Exception {
    TestCase tc = new TestCase();
    tc.setGlobFilter("filter");
    assertNotNull(tc.getGlobFilter());
    assertEquals("filter", tc.getGlobFilter());
  }

  @org.junit.Test
  public void testExecuteSkipTest() throws Exception {
    TestCase tc = new TestCase();
    tc.setUniqueId("testcase");
    tc.setGlobFilter("filter");
    JUnitReportTestCase result = tc.execute("testlist", new StubClient(), new ServiceToTest(), new ServiceTestConfig());
    assertNotNull(result);
    assertNotNull(result.getIssue());
    assertEquals(1, result.getIssue().size());
    assertTrue(result.getIssue().get(0) instanceof JUnitReportSkipped);
    assertNotNull(result.getTime());
  }

  @org.junit.Test
  public void testExecute() throws Exception {
    TestCase tc = new TestCase();
    tc.setUniqueId("testcase");
    tc.setAssertions(new StubAssertions(null));
    JUnitReportTestCase result = tc.execute("testlist", new StubClient(), createServiceToTest(), new ServiceTestConfig());
    assertNotNull(result);
    assertNull(result.getIssue());
    assertNotNull(result.getTime());
  }

  @org.junit.Test
  public void testExecuteFailed() throws Exception {
    TestCase tc = new TestCase();
    tc.setUniqueId("testcase");
    tc.setAssertions(new StubAssertions(new JUnitReportFailure("failure")));
    JUnitReportTestCase result = tc.execute("testlist", new StubClient(), createServiceToTest(), new ServiceTestConfig());
    assertNotNull(result);
    assertNotNull(result.getIssue());
    assertEquals(1, result.getIssue().size());
    assertTrue(result.getIssue().get(0) instanceof JUnitReportFailure);
    assertNotNull(result.getTime());
  }

  @org.junit.Test
  public void testExecuteExpectedExceptionButNone() throws Exception {
    TestCase tc = new TestCase();
    tc.setUniqueId("testcase");
    tc.setExpectedException(new ExpectedException());
    tc.setAssertions(new StubAssertions(null));
    JUnitReportTestCase result = tc.execute("testlist", new StubClient(), createServiceToTest(), new ServiceTestConfig());
    assertNotNull(result);
    assertNotNull(result.getIssue());
    assertEquals(1, result.getIssue().size());
    assertTrue(result.getIssue().get(0) instanceof JUnitReportFailure);
    assertEquals("Assertion Failure: Expected Exception [com.adaptris.core.ServiceException]", ((JUnitReportFailure) result.getIssue().get(0)).getMessage());
    assertNotNull(result.getTime());
  }

  @org.junit.Test
  public void testExecuteExpectedExceptionThrown() throws Exception {
    TestCase tc = new TestCase();
    tc.setUniqueId("testcase");
    tc.setExpectedException(new ExpectedException("com.adaptris.tester.runtime.ServiceTestException"));
    tc.setAssertions(new StubAssertions(null));
    JUnitReportTestCase result = tc.execute("testlist", new StubClientAlwaysFails(), createServiceToTest(), new ServiceTestConfig());
    assertNotNull(result);
    assertNull(result.getIssue());
    assertNotNull(result.getTime());
  }

  @org.junit.Test
  public void testExecuteExpectedExceptionThrownButNotExpected() throws Exception {
    TestCase tc = new TestCase();
    tc.setUniqueId("testcase");
    tc.setExpectedException(new ExpectedException("com.adaptris.core.ServiceException"));
    tc.setAssertions(new StubAssertions(null));
    JUnitReportTestCase result = tc.execute("testlist", new StubClientAlwaysFails(), createServiceToTest(), new ServiceTestConfig());
    assertNotNull(result);
    assertNotNull(result.getIssue());
    assertEquals(1, result.getIssue().size());
    assertTrue(result.getIssue().get(0) instanceof JUnitReportFailure);
    assertEquals("Assertion Failure: Expected Exception [com.adaptris.core.ServiceException]", ((JUnitReportFailure) result.getIssue().get(0)).getMessage());
    assertNotNull(result.getTime());
  }

  @org.junit.Test
  public void testExecuteExceptionThrownButNotExpected() throws Exception {
    TestCase tc = new TestCase();
    tc.setUniqueId("testcase");
    tc.setAssertions(new StubAssertions(null));
    JUnitReportTestCase result = tc.execute("testlist", new StubClientAlwaysFails(), createServiceToTest(), new ServiceTestConfig());
    assertNotNull(result);
    assertNotNull(result.getIssue());
    assertEquals(1, result.getIssue().size());
    assertTrue(result.getIssue().get(0) instanceof JUnitReportError);
    assertEquals("Test Error: [com.adaptris.tester.runtime.ServiceTestException]", ((JUnitReportError) result.getIssue().get(0)).getMessage());
    assertNotNull(result.getTime());
  }

  @org.junit.Test
  public void testIsTestToBeExecuted() throws Exception {
    System.setProperty("test.glob.filter", "testlist.test.testcase");
    TestCase tc = new TestCase();
    assertTrue(tc.isTestToBeExecuted("testlist.test.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.other"));
    System.clearProperty("test.glob.filter");
    tc.setGlobFilter(null);
    assertTrue(tc.isTestToBeExecuted("testlist.test.testcase"));
    assertTrue(tc.isTestToBeExecuted("testlist.test.other"));
    assertTrue(tc.isTestToBeExecuted("testlist.test.?.testcase"));
    assertTrue(tc.isTestToBeExecuted("testlist.test.*.testcase"));
    assertTrue(tc.isTestToBeExecuted("testlist.test.1.testcase"));
    assertTrue(tc.isTestToBeExecuted("testlist.test.10.testcase"));
    tc.setGlobFilter("testlist.test.testcase");
    assertTrue(tc.isTestToBeExecuted("testlist.test.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.other"));
    tc.setGlobFilter("testlist.test.*");
    assertTrue(tc.isTestToBeExecuted("testlist.test.testcase"));
    assertTrue(tc.isTestToBeExecuted("testlist.test.other"));
    tc.setGlobFilter("*.testcase");
    assertTrue(tc.isTestToBeExecuted("testlist.test.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.other"));
    tc.setGlobFilter("testlist.test.?.testcase");
    assertTrue(tc.isTestToBeExecuted("testlist.test.1.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.10.testcase"));
    tc.setGlobFilter("testlist.test.\\?.testcase");
    assertTrue(tc.isTestToBeExecuted("testlist.test.?.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.1.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.10.testcase"));
    tc.setGlobFilter("testlist.test.\\*.testcase");
    assertTrue(tc.isTestToBeExecuted("testlist.test.*.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.1.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.testcase"));
    assertFalse(tc.isTestToBeExecuted("testlist.test.10.testcase"));
  }

  private class StubAssertions extends Assertions {

    private final JUnitReportTestIssue result;

    StubAssertions(JUnitReportTestIssue result) {
      this.result = result;
    }

    @Override
    public JUnitReportTestIssue execute(TestMessage actual, ServiceTestConfig config) {
      return result;
    }

  }

  private class StubClientAlwaysFails extends StubClient {

    @Override
    public TestMessage applyService(String xml, TestMessage message) throws ServiceTestException {
      throw new ServiceTestException();
    }
  }

  private ServiceToTest createServiceToTest() {
    ServiceToTest st = new ServiceToTest();
    InlineSource source = new InlineSource();
    source.setXml("<xml />");
    st.setSource(source);
    return st;
  }

}
