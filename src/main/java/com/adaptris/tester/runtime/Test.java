package com.adaptris.tester.runtime;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adaptris.tester.report.junit.JUnitReportTestSuite;
import com.adaptris.tester.runtime.clients.TestClient;
import com.adaptris.tester.runtime.services.ServiceToTest;
import com.adaptris.tester.runtime.services.preprocessor.VarSubPropsPreprocessor;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 *
 * @service-test-config test
 */
@XStreamAlias("test")
public class Test implements TestComponent {

  private transient Logger log = LoggerFactory.getLogger(this.getClass().getName());

  private String uniqueId;
  @XStreamImplicit
  private List<TestCase> testCases;
  private ServiceToTest serviceToTest;

  public Test(){
    setTestCases(new ArrayList<TestCase>());
    setServiceToTest(new ServiceToTest());
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  public void setServiceToTest(ServiceToTest serviceToTest) {
    this.serviceToTest = serviceToTest;
  }

  public ServiceToTest getServiceToTest() {
    return serviceToTest;
  }

  public void setTestCases(List<TestCase> testCases) {
    this.testCases = testCases;
  }

  public List<TestCase> getTestCases() {
    return testCases;
  }

  public void addTestCase(TestCase testCase){
    testCases.add(testCase);
  }

  JUnitReportTestSuite execute(String parentName, TestClient client, Map<String, String> helperProperties) throws ServiceTestException {
    String fqName = parentName + "." + getUniqueId();
    log.info("Running [{}]", fqName);
    JUnitReportTestSuite result = new JUnitReportTestSuite(fqName);
    if (helperProperties.size() > 0) {
      getServiceToTest().addPreprocessor(new VarSubPropsPreprocessor(helperProperties));
    }
    long startTime = System.nanoTime();
    for (TestCase testCase : getTestCases()) {
      result.addTestCase(testCase.execute(fqName, client, getServiceToTest()));
    }
    long endTime = System.nanoTime();
    long elapsedTime = endTime - startTime;
    result.setTime(elapsedTime / 1000000000.0);
    DecimalFormat df = new DecimalFormat("0.000");
    df.setRoundingMode(RoundingMode.CEILING);
    log.info("Tests run: {}, Failures: {}, Errors: {}, Skipped: {}, Time elapsed: {} sec",
        result.getTests(), result.getFailures(), result.getErrors(), result.getSkipped(), df.format(result.getTime()));
    return result;
  }
}
