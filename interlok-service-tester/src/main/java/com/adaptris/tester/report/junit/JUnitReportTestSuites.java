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
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import com.adaptris.core.CoreException;
import com.adaptris.core.DefaultMarshaller;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.clients.TestClient;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Child of {@link JUnitReportTestResults} for storing results.
 *
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 *
 * <p>
 * When tests are executed via {@link com.adaptris.tester.runtime.Test#execute(String, TestClient, ServiceTestConfig)} the results
 * are produced as this corresponding class.
 * </p>
 */
@XStreamAlias("testsuites")
public class JUnitReportTestSuites {

  private final String name;
  @XStreamImplicit
  private final List<JUnitReportTestSuite> testSuites;

  public JUnitReportTestSuites(String name){
    this.name = name;
    testSuites = new ArrayList<>();
  }

  /**
   * Returns test suites name
   * @return Test suites name
   */
  public String getName() {
    return name;
  }

  /**
   * Adds {@link JUnitReportTestSuite} to list.
   *
   * @param testSuite {@link JUnitReportTestSuite} to be added to list.
   */
  public void addTestSuite(JUnitReportTestSuite testSuite){
    testSuites.add(testSuite);
  }

  /**
   * Returns list of {@link JUnitReportTestSuite}.
   *
   * @return list of {@link JUnitReportTestSuite}
   */
  public List<JUnitReportTestSuite> getTestSuites() {
    return testSuites;
  }

  /**
   * Produce result files to output directory by unmarshalling each {@link JUnitReportTestSuite}
   * returned by {@link #getTestSuites()}
   *
   * <p>Files produces to <code>outputDirectory/TEST-{@link JUnitReportTestSuite#getName()}.xml</code></p>
   *
   * @param outputDirectory output directory to produce test results
   * @throws ServiceTestException wraps any thrown exception
   */
  public void writeReports(final File outputDirectory) throws ServiceTestException{
    try {
      for (JUnitReportTestSuite suite : testSuites) {
        String result = DefaultMarshaller.getDefaultMarshaller().marshal(suite);
        FileUtils.writeStringToFile(new File(outputDirectory, "TEST-" + suite.getName() + ".xml"), result, StandardCharsets.UTF_8);
      }
    } catch (CoreException | IOException e) {
      throw new ServiceTestException(e);
    }
  }

  /**
   * Checks the list of stored {@link JUnitReportTestSuite} has any failures using
   * {@link JUnitReportTestSuite#hasFailures()}.
   *
   * @return true if {@link JUnitReportTestSuite} has any failures.
   */
  boolean hasFailures(){
    for(JUnitReportTestSuite testSuites : this.testSuites) {
      if (testSuites.hasFailures()){
        return true;
      }
    }
    return false;
  }

}
