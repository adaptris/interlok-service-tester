package com.adaptris.tester.report.junit;

import com.adaptris.core.stubs.TempFileUtils;
import com.adaptris.util.GuidGenerator;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class JUnitReportTestSuitesTest {

  @Test
  public void testGetName() throws Exception {
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    assertEquals("suitesname", ss.getName());
  }

  @Test
  public void testGetTestSuites() throws Exception {
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportFailure("message"));
    s.addTestCase(tc);
    ss.addTestSuite(s);
    assertEquals(1, ss.getTestSuites().size());
  }

  @Test
  public void writeReports() throws Exception {
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportFailure("message"));
    s.addTestCase(tc);
    ss.addTestSuite(s);
    ss.writeReports(tempDir);
    File expectedFile = new File(tempDir, "TEST-suitename.xml");
    assertTrue(expectedFile.exists());
  }

  @Test
  public void hasFailuresFailures() throws Exception {
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportFailure("message"));
    s.addTestCase(tc);
    ss.addTestSuite(s);
    assertTrue(ss.hasFailures());
  }

  @Test
  public void hasFailuresErrors() throws Exception {
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportError("message"));
    s.addTestCase(tc);
    ss.addTestSuite(s);
    assertTrue(ss.hasFailures());
  }

  @Test
  public void hasFailuresNone() throws Exception {
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(null);
    s.addTestCase(tc);
    ss.addTestSuite(s);
    assertFalse(ss.hasFailures());
  }

  @Test
  public void hasFailuresNoneSkipped() throws Exception {
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportSkipped());
    s.addTestCase(tc);
    ss.addTestSuite(s);
    assertFalse(ss.hasFailures());
  }

}