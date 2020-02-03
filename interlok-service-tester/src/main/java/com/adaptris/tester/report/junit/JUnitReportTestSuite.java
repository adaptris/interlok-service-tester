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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.clients.TestClient;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Child of {@link JUnitReportTestSuites} for storing results.
 *
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 *
 * <p>
 * When tests are executed via {@link com.adaptris.tester.runtime.Test#execute(String, TestClient, ServiceTestConfig)} the results
 * are produced as this corresponding class.
 * </p>
 *
 * @junit-config testsuite
 */
@XStreamAlias("testsuite")
public class JUnitReportTestSuite {

  @XStreamAsAttribute
  private String name;
  @XStreamAsAttribute
  private String hostname;
  @XStreamAsAttribute
  private int failures;
  @XStreamAsAttribute
  private int errors;
  @XStreamAsAttribute
  private int tests;
  @XStreamAsAttribute
  private int skipped;
  @XStreamAsAttribute
  private String timestamp;
  @XStreamAsAttribute
  private double time;

  private final List<JUnitReportProperty> properties = new ArrayList<>();

  @XStreamImplicit
  private final List<JUnitReportTestCase> testCases = new ArrayList<>();

  public JUnitReportTestSuite(String name){
    this.name = name;
    this.failures = 0;
    this.errors = 0;
    this.tests = 0;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
    this.timestamp = sdf.format(date);
    try {
      InetAddress ip = InetAddress.getLocalHost();
      hostname = ip.getHostName();
    } catch (UnknownHostException e) {
      hostname = null;
    }
  }

  /**
   * Adds JUnit property to list
   * @param property JUnit property
   */
  public void addProperty(JUnitReportProperty property){
    properties.add(property);
  }

  /**
   * Returns list of JUnit properties
   * @return list of JUnit properties
   */
  public List<JUnitReportProperty> getProperties() {
    return properties;
  }

  /**
   * Adds JUnit test case to list, also increments test count and conditionality increments failure, error and skipped counts.
   * @param testCase JUnit test case
   */
  public void addTestCase(JUnitReportTestCase testCase){
    this.testCases.add(testCase);
    this.tests++;
    if(testCase.isFailure()){
      this.failures++;
    }
    if(testCase.isError()){
      this.errors++;
    }
    if(testCase.isSkipped()){
      this.skipped ++;
    }
  }

  /**
   * Returns list of test cases
   * @return list of test cases
   */
  public List<JUnitReportTestCase> getTestCases() {
    return testCases;
  }

  /**
   * Checks whether any of the stored test cases have a failure or error.
   * @return true if any test case has failure or error.
   */
  boolean hasFailures(){
    for(JUnitReportTestCase testCase : this.testCases) {
      if (testCase.isFailure() || testCase.isError()){
        return true;
      }
    }
    return false;
  }

  /**
   * Returns test suite name
   * @return Test suite name
   */
  public String getName() {
    return name;
  }

  /**
   * Set test suite time
   * @param time Test suite time
   */
  public void setTime(double time) {
    this.time = time;
  }

  /**
   * Returns test suite time
   * @return Test suite time
   */
  public double getTime() {
    return time;
  }

  /**
   * Returns number of tests in suite
   * @return Number of tests in suite
   */
  public int getTests() {
    return tests;
  }

  /**
   * Returns number of tests with an failure in suite
   * @return Number of tests with an failure in suite
   */
  public int getFailures() {
    return failures;
  }

  /**
   * Returns number of tests with an error in suite
   * @return Number of tests with an error in suite
   */
  public int getErrors() {
    return errors;
  }

  /**
   * Returns number of tests with an skipped in suite
   * @return Number of tests with an skipped in suite
   */
  public int getSkipped() {
    return skipped;
  }

  /**
   * Returns hostname test suite executed on
   * @return Hostname test suite executed on
   */
  public String getHostname() {
    return hostname;
  }

  /**
   * Returns timestamp test suite started on, format: yyyy-MM-dd'T'hh:mm:ss
   * @return Timestamp test suite started on
   */
  public String getTimestamp() {
    return timestamp;
  }
}
