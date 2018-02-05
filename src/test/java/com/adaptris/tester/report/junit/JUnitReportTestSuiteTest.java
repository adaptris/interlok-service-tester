package com.adaptris.tester.report.junit;

import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.*;

public class JUnitReportTestSuiteTest {

  private static final String NAME = "suite";

  @Test
  public void getProperties() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    s.addProperty(new JUnitReportProperty("name", "value"));
    assertEquals(1, s.getProperties().size());
    assertEquals("name", s.getProperties().get(0).getName());
    assertEquals("value", s.getProperties().get(0).getValue());
  }

  @Test
  public void getTestCases() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    s.addTestCase(new JUnitReportTestCase("testcase"));
    assertEquals(1, s.getTestCases().size());
    assertEquals("testcase", s.getTestCases().get(0).getName());
  }

  @Test
  public void hasFailuresFailures() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportFailure("message"));
    s.addTestCase(tc);
    assertTrue(s.hasFailures());
  }

  @Test
  public void hasFailuresErrors() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportError("message"));
    s.addTestCase(tc);
    assertTrue(s.hasFailures());
  }

  @Test
  public void hasFailuresNone() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(null);
    s.addTestCase(tc);
    assertFalse(s.hasFailures());
  }

  @Test
  public void hasFailuresNoneSkipped() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportSkipped());
    s.addTestCase(tc);
    assertFalse(s.hasFailures());
  }

  @Test
  public void getName() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    assertEquals(NAME, s.getName());
  }

  @Test
  public void getTime() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    s.setTime(10);
    assertEquals(10, s.getTime(), 0.0);
  }

  @Test
  public void getTests() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(null);
    s.addTestCase(tc);
    assertEquals(1, s.getTests());
    assertEquals(0, s.getFailures());
    assertEquals(0, s.getErrors());
    assertEquals(0, s.getSkipped());
  }

  @Test
  public void getFailures() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportFailure("message"));
    s.addTestCase(tc);
    assertEquals(1, s.getTests());
    assertEquals(1, s.getFailures());
    assertEquals(0, s.getErrors());
    assertEquals(0, s.getSkipped());
  }

  @Test
  public void getErrors() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportError("message"));
    s.addTestCase(tc);
    assertEquals(1, s.getTests());
    assertEquals(0, s.getFailures());
    assertEquals(1, s.getErrors());
    assertEquals(0, s.getSkipped());
  }

  @Test
  public void getSkipped() throws Exception {
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    JUnitReportTestCase tc = new JUnitReportTestCase("testcase");
    tc.setTestIssue(new JUnitReportSkipped());
    s.addTestCase(tc);
    assertEquals(1, s.getTests());
    assertEquals(0, s.getFailures());
    assertEquals(0, s.getErrors());
    assertEquals(1, s.getSkipped());
  }

  @Test
  public void getHostname() throws Exception{
    InetAddress ip = InetAddress.getLocalHost();
    final String hostname = ip.getHostName();
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    assertEquals(hostname, s.getHostname());
  }

  @Test
  public void getTimestamp() throws Exception{
    JUnitReportTestSuite s = new JUnitReportTestSuite(NAME);
    assertNotNull(s.getTimestamp());
  }

}