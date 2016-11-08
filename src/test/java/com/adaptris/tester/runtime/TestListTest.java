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
    JUnitReportTestSuites s = tl.execute(new StubClient(), new HashMap<String, String>());
    assertNotNull(s);
    assertEquals(1, s.getTestSuites().size());
    assertEquals("id", s.getName());
  }

}