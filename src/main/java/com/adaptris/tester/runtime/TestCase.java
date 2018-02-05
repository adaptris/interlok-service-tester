package com.adaptris.tester.runtime;

import com.adaptris.tester.report.junit.*;
import com.adaptris.tester.runtime.clients.TestClient;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.messages.TestMessageProvider;
import com.adaptris.tester.runtime.services.ServiceToTest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 *
 * @service-test-config test-case
 */
@XStreamAlias("test-case")
public class TestCase implements TestComponent {

  private static final String TEST_FILTER =  "test.glob.filter";

  private String uniqueId;
  private TestMessageProvider inputMessageProvider;
  private Assertions assertions;
  private ExpectedException expectedException;
  @XStreamOmitField
  private String globFilter;

  public TestCase(){
    setAssertions(new Assertions());
    setExpectedException(null);
    setInputMessageProvider(new TestMessageProvider());
    if (System.getProperties().containsKey(TEST_FILTER)) {
      setGlobFilter(System.getProperty(TEST_FILTER));
    }
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  public TestMessageProvider getInputMessageProvider() {
    return inputMessageProvider;
  }

  public void setInputMessageProvider(TestMessageProvider inputMessageProvider) {
    this.inputMessageProvider = inputMessageProvider;
  }

  public void setAssertions(Assertions assertions) {
    this.assertions = assertions;
  }

  public Assertions getAssertions() {
    return assertions;
  }

  public void setExpectedException(ExpectedException expectedException) {
    this.expectedException = expectedException;
  }

  public ExpectedException getExpectedException() {
    return expectedException;
  }

  private String globFilter(){
    return getGlobFilter() == null ? "*" : getGlobFilter();
  }

  public void setGlobFilter(String globFilter) {
    this.globFilter = globFilter;
  }

  public String getGlobFilter() {
    return globFilter;
  }

  boolean isTestToBeExecuted(final String fqName){
    String regexFilter = createRegexFromGlob(globFilter());
    return fqName.matches(regexFilter);
  }

  JUnitReportTestCase execute(String parentId, TestClient client, ServiceToTest serviceToTest) throws ServiceTestException {
    final String fqName = parentId + "." + getUniqueId();

    JUnitReportTestCase result = new JUnitReportTestCase(getUniqueId());
    if(!isTestToBeExecuted(fqName)){
      result.setTestIssue(new JUnitReportSkipped());
      result.setTime(0);
      return result;
    }
    long startTime = System.nanoTime();
    try {
      TestMessage input;
      input = getInputMessageProvider().createTestMessage();
      TestMessage returnMessage = client.applyService(serviceToTest.getProcessedSource(), input);
      if(getExpectedException() != null){
        //Exception should have been thrown
        result.setTestIssue(new JUnitReportFailure("Assertion Failure: Expected Exception [" + getExpectedException().getClassName() + "]", "No Exception thrown"));
      } else {
        result.setTestIssue(getAssertions().execute(returnMessage));
      }
    } catch (Exception e){
      if(getExpectedException() == null){
        JUnitReportError issue = new JUnitReportError("Test Error: [" + e.toString() + "]", ExceptionUtils.getStackTrace(e));
        result.setTestIssue(issue);
      } else {
        JUnitReportTestIssue issue = getExpectedException().check(e);
        result.setTestIssue(issue);
      }

    }
    long endTime = System.nanoTime();
    long elapsedTime = endTime - startTime;
    result.setTime((double)elapsedTime / 1000000000.0);
    return result;
  }

  private String createRegexFromGlob(String glob)
  {
    String out = "^";
    boolean escaping = false;
    for(int i = 0; i < glob.length(); ++i)
    {
      final char c = glob.charAt(i);
      switch(c)
      {
        case '*':
          if (escaping){
            out += "*";
          } else {
            out += ".*";
          }
          escaping = false;
          break;
        case '?':
          if (escaping){
            out += "?";
          } else {
            out += '.';
          }
          escaping = false;
          break;
        case '.':
          out += "\\.";
          break;
        case '\\':
          out += "\\";
          escaping = true;
          break;
        default:
          out += c;
      }
    }
    out += '$';
    return out;
  }

}
