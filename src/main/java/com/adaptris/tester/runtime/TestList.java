package com.adaptris.tester.runtime;

import com.adaptris.tester.report.junit.JUnitReportTestSuite;
import com.adaptris.tester.report.junit.JUnitReportTestSuites;
import com.adaptris.tester.runtime.clients.TestClient;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.*;

@XStreamAlias("test-list")
public class TestList extends AbstractCollection<Test> implements TestComponent {

  private String uniqueId;
  @XStreamImplicit
  private List<Test> testCases;

  public TestList(){
    setTests(new ArrayList<Test>());
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  public void setTests(List<Test> testCases) {
    this.testCases = testCases;
  }

  public List<Test> getTests() {
    return testCases;
  }

  public void addTest(Test test){
    add(test);
  }

  public boolean add(Test test){
    return this.testCases.add(test);
  }

  @Override
  public Iterator<Test> iterator() {
    return testCases.listIterator();
  }

  @Override
  public int size() {
    return testCases.size();
  }

  JUnitReportTestSuites execute(TestClient client, Map<String, String> helperProperties) throws ServiceTestException {
    JUnitReportTestSuites result = new JUnitReportTestSuites(getUniqueId());
    for (Test testCase : getTests()) {
      JUnitReportTestSuite suite = testCase.execute(getUniqueId(), client, helperProperties);
      result.addTestSuite(suite);
    }
    return result;
  }
}
