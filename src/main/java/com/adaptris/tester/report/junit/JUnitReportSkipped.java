package com.adaptris.tester.report.junit;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Skipped implementation of {@link JUnitReportTestIssue} used in {@link JUnitReportTestCase} for storing results.
 *
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 *
 * <p>When tests are executed via {@link com.adaptris.tester.runtime.TestCase#execute(String, com.adaptris.tester.runtime.clients.TestClient, com.adaptris.tester.runtime.services.ServiceToTest)}
 * this class is used to set the test issue if applicable using: {@link JUnitReportTestCase#setTestIssue(JUnitReportTestIssue)}.</p>
 *
 * @junit-config skipped
 */
@XStreamAlias("skipped")
public class JUnitReportSkipped implements JUnitReportTestIssue {

}
