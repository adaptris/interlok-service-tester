package com.adaptris.tester.runtime.messages.assertion;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals {@link #getExpectedLines()}
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 *
 * @service-test-config assert-line-payload-equals
 */
@XStreamAlias("assert-line-payload-equals")
public class AssertLinePayloadEquals implements Assertion {

  @Deprecated
  private String uniqueId;

  @XStreamImplicit(itemFieldName = "line")
  private List<String> expectedLines;

  public AssertLinePayloadEquals(){
    this(new ArrayList<>());
  }

  public AssertLinePayloadEquals(List<String> expectedLines){
    setExpectedLines(expectedLines);
  }

  @Override
  public AssertionResult execute(TestMessage actual, ServiceTestConfig config) throws ServiceTestException {
    try {
      List<String> actualLines = IOUtils.readLines(new StringReader(actual.getPayload()));
      return new AssertionResult("assert-lines-payload-equals", expectedLines.equals(actualLines));
    } catch (IOException e) {
      throw new ServiceTestException(e);
    }
  }

  @Override
  @Deprecated
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }


  public void setExpectedLines(List<String> expectedLines) {
    this.expectedLines = expectedLines;
  }

  public List<String> getExpectedLines() {
    return expectedLines;
  }

  @Override
  public String expected() {
    StringBuilder expected = new StringBuilder();
    for(String lines : getExpectedLines()){
      expected.append(lines).append(System.lineSeparator());
    }
    return expected.toString();
  }

  @Override
  public boolean showReturnedMessage() {
    return true;
  }
}
