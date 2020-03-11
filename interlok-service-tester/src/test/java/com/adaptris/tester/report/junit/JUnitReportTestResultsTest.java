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
