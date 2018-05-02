package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals {@link #getPayload()}
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 *
 * @service-test-config assert-json-payload-equals
 */
@XStreamAlias("assert-json-payload-equals")
public class AssertJsonPayloadEquals extends PayloadAssertion {

  public AssertJsonPayloadEquals(){
    super();
  }

  public AssertJsonPayloadEquals(String payload){
    super(payload);
  }

  @Override
  public AssertionResult execute(String actual) throws ServiceTestException {
    try {
      JSONCompareResult result = JSONCompare.compareJSON(getPayload(), actual, JSONCompareMode.STRICT_ORDER);
      return new AssertionResult(getUniqueId(), "assert-json-payload-equals", !result.failed(),
          "Assertion Failure: [assert-json-payload-equals]\n" + result.getMessage());
    } catch (JSONException e) {
      throw new ServiceTestException(e);
    }
  }
}
