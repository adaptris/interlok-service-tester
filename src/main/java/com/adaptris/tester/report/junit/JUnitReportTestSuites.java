package com.adaptris.tester.report.junit;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.adaptris.core.CoreException;
import com.adaptris.core.DefaultMarshaller;
import com.adaptris.tester.runtime.ServiceTestException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Child of {@link JUnitReportTestResults} for storing results.
 *
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 *
 * <p>When tests are executed via {@link com.adaptris.tester.runtime.TestList#execute(com.adaptris.tester.runtime.clients.TestClient, java.util.Map)}
 * the results are produced as this corresponding class.</p>
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
