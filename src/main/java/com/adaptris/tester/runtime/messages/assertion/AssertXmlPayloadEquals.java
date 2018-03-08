package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals {@link #getPayload()}
 *
 * @service-test-config assert-payload-equals
 */
@XStreamAlias("assert-xml-payload-equals")
public class AssertXmlPayloadEquals extends PayloadAssertion {

  public AssertXmlPayloadEquals(){
    super();
  }

  public AssertXmlPayloadEquals(String payload){
    super(payload);
  }

  @Override
  public AssertionResult execute(String actual) throws ServiceTestException {
    try {
      XMLUnit.setIgnoreWhitespace(true);
      XMLUnit.setIgnoreAttributeOrder(true);
      DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(getPayload(), actual));
      List<?> allDifferences = diff.getAllDifferences();
      return new AssertionResult(getUniqueId(), "assert-xml-payload-equals", allDifferences.size() == 0,
          "Assertion Failure: [assert-xml-payload-equals]\n" + diff.toString());
    } catch (SAXException | IOException e) {
      throw new ServiceTestException(e);
    }
  }
}
