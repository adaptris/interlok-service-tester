package com.adaptris.tester.report.junit;

import com.adaptris.core.stubs.TempFileUtils;
import com.adaptris.util.GuidGenerator;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JUnitReportTestResultsTest {

  @Test
  public void writeReports() throws Exception {
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    JUnitReportTestResults r = new JUnitReportTestResults("resultsname");
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportFailure("message"));
    s.addTestCase(tc);
    ss.addTestSuite(s);
    r.addTestSuites(ss);
    r.writeReports(tempDir);
    File expectedFile = new File(tempDir, "TEST-suitename.xml");
    assertTrue(expectedFile.exists());
  }

  @Test
  public void hasFailuresFailures() throws Exception {
    JUnitReportTestResults r = new JUnitReportTestResults("resultsname");
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportFailure("message"));
    s.addTestCase(tc);
    ss.addTestSuite(s);
    r.addTestSuites(ss);
    assertTrue(r.hasFailures());
  }

  @Test
  public void hasFailuresErrors() throws Exception {
    JUnitReportTestResults r = new JUnitReportTestResults("resultsname");
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportError("message"));
    s.addTestCase(tc);
    ss.addTestSuite(s);
    r.addTestSuites(ss);
    assertTrue(r.hasFailures());
  }

  @Test
  public void hasFailuresNone() throws Exception {
    JUnitReportTestResults r = new JUnitReportTestResults("resultsname");
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(null);
    s.addTestCase(tc);
    ss.addTestSuite(s);
    r.addTestSuites(ss);
    assertFalse(r.hasFailures());
  }

  @Test
  public void hasFailuresNoneSkipped() throws Exception {
    JUnitReportTestResults r = new JUnitReportTestResults("resultsname");
    JUnitReportTestSuites ss = new JUnitReportTestSuites("suitesname");
    JUnitReportTestSuite s = new JUnitReportTestSuite("suitename");
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportSkipped());
    s.addTestCase(tc);
    ss.addTestSuite(s);
    r.addTestSuites(ss);
    assertFalse(r.hasFailures());
  }

}