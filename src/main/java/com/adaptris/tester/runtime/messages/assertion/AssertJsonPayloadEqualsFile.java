package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * Checks if {@link TestMessage#getPayload()} equals file contents.
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 *
 * @service-test-config assert-json-payload-equals-file
 */
@XStreamAlias("assert-json-payload-equals-file")
public class AssertJsonPayloadEqualsFile extends AssertPayloadEqualsFile {


  public AssertJsonPayloadEqualsFile(){
  }

  public AssertJsonPayloadEqualsFile(String file){
    setFile(file);
  }

  public AssertionResult checkResults(String actual, String expected) throws ServiceTestException{
    try {
      JSONCompareResult result = JSONCompare.compareJSON(expected, actual, JSONCompareMode.STRICT);
      return new AssertionResult(getUniqueId(), "assert-json-payload-equals-file", !result.failed(),
          "Assertion Failure: [assert-json-payload-equals-file]\n" + result.getMessage());
    } catch (JSONException e) {
      throw new ServiceTestException(e);
    }
  }
}
