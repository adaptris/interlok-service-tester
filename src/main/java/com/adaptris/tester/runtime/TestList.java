package com.adaptris.tester.runtime;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.adaptris.tester.report.junit.JUnitReportTestSuite;
import com.adaptris.tester.report.junit.JUnitReportTestSuites;
import com.adaptris.tester.runtime.clients.TestClient;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 *
 * @service-test-config test-list
 */
@XStreamAlias("test-list")
public class TestList extends AbstractCollection<Test> implements TestComponent {

  private String uniqueId;
  @XStreamImplicit
  private List<Test> tests;

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

  public void setTests(List<Test> tests) {
    this.tests = tests;
  }

  public List<Test> getTests() {
    return tests;
  }

  public void addTest(Test test){
    add(test);
  }

  @Override
  public boolean add(Test test){
    return tests.add(test);
  }

  @Override
  public Iterator<Test> iterator() {
    return tests.listIterator();
  }

  @Override
  public int size() {
    return tests.size();
  }

  JUnitReportTestSuites execute(TestClient client, Map<String, String> helperProperties) throws ServiceTestException {
    JUnitReportTestSuites result = new JUnitReportTestSuites(getUniqueId());
    for (Test tests : getTests()) {
      JUnitReportTestSuite suite = tests.execute(getUniqueId(), client, helperProperties);
      result.addTestSuite(suite);
    }
    return result;
  }
}
