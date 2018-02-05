package com.adaptris.tester.report.junit;

import com.adaptris.tester.runtime.ServiceTest;
import com.adaptris.tester.runtime.ServiceTestException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for storing results.
 *
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 *
 * <p>When tests are executed via {@link ServiceTest#execute()} the results are produced as this corresponding class.</p>
 */
public class JUnitReportTestResults {

  private final String name;
  private final List<JUnitReportTestSuites> testSuites;

  public JUnitReportTestResults(final String name){
    this.name = name;
    this.testSuites = new ArrayList<>();
  }

  /**
   * Adds {@link JUnitReportTestSuites} to list.
   *
   * @param testSuites {@link JUnitReportTestSuites} to be added to list.
   */
  public void addTestSuites(final JUnitReportTestSuites testSuites){
    this.testSuites.add(testSuites);
  }

  /**
   * Executes {@link JUnitReportTestSuites#writeReports(File)} for the list of stored {@link JUnitReportTestSuites}
   *
   * @param outputDirectory Output directory passed on to {@link JUnitReportTestSuites#writeReports(File)}
   * @throws ServiceTestException wrapping any thrown exception
   */
  public void writeReports(final File outputDirectory) throws ServiceTestException {
    for(JUnitReportTestSuites testSuites : this.testSuites) {
      testSuites.writeReports(outputDirectory);
    }
  }

  /**
   * Checks the list of stored {@link JUnitReportTestSuites} has any failures using
   * {@link JUnitReportTestSuites#hasFailures()}.
   *
   * @return true if {@link JUnitReportTestSuites} has any failures.
   */
  public boolean hasFailures(){
    for(JUnitReportTestSuites testSuites : this.testSuites) {
      if (testSuites.hasFailures()){
        return true;
      }
    }
    return false;
  }

}
