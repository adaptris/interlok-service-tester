package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals lines of {@link #getFile()}
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 *
 * @service-test-config assert-line-payload-equals-file
 */
@XStreamAlias("assert-line-payload-equals-file")
public class AssertLinePayloadEqualsFile extends AssertPayloadEqualsFile {

  public AssertLinePayloadEqualsFile(){
    super();
  }

  public AssertLinePayloadEqualsFile(String file){
    super(file);
  }

  public AssertionResult checkResults(String actual, String expected) throws ServiceTestException{
    try {
      List<String> expectedLines = IOUtils.readLines(new StringReader(expected));
      List<String> actualLines = IOUtils.readLines(new StringReader(actual));
      return new AssertionResult(getUniqueId(), "assert-lines-payload-equals", expectedLines.equals(actualLines));
    } catch (IOException e) {
      throw new ServiceTestException(e);
    }
  }
}
