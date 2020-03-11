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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.adaptris.tester.runtime.ServiceTest;
import com.adaptris.tester.runtime.ServiceTestException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Main class for storing results.
 *
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 *
 * <p>When tests are executed via {@link ServiceTest#execute()} the results are produced as this corresponding class.</p>
 */
@XStreamAlias("testresults")
public class JUnitReportTestResults {

  private final String name;
  @XStreamImplicit
  private final List<JUnitReportTestSuites> testSuites;

  public JUnitReportTestResults(final String name){
    this.name = name;
    testSuites = new ArrayList<>();
  }

  /**
   * Returns test results name
   *
   * @return Test results name
   */
  public String getName() {
    return name;
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
   * Returns list of {@link JUnitReportTestSuites}.
   *
   * @return list of {@link JUnitReportTestSuites}
   */
  public List<JUnitReportTestSuites> getTestSuites() {
    return testSuites;
  }

  /**
   * Executes {@link JUnitReportTestSuites#writeReports(File)} for the list of stored {@link JUnitReportTestSuites}
   *
   * @param outputDirectory Output directory passed on to {@link JUnitReportTestSuites#writeReports(File)}
   * @throws ServiceTestException wrapping any thrown exception
   */
  public void writeReports(final File outputDirectory) throws ServiceTestException {
    for (JUnitReportTestSuites testSuites : testSuites) {
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
    for (JUnitReportTestSuites testSuites : testSuites) {
      if (testSuites.hasFailures()){
        return true;
      }
    }
    return false;
  }

}
