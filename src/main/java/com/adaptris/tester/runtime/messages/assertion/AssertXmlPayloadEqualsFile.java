package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * Checks if {@link TestMessage#getPayload()} equals file contents.
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 *
 * @service-test-config assert-xml-payload-equals-file
 */
@XStreamAlias("assert-xml-payload-equals-file")
public class AssertXmlPayloadEqualsFile extends AssertPayloadEqualsFile {


  public AssertXmlPayloadEqualsFile(){
  }

  public AssertXmlPayloadEqualsFile(String file){
    setFile(file);
  }

  public AssertionResult checkResults(String actual, String expected) throws ServiceTestException{
    try {
      XMLUnit.setIgnoreWhitespace(true);
      XMLUnit.setIgnoreAttributeOrder(true);
      DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expected, actual));
      List<?> allDifferences = diff.getAllDifferences();
      return new AssertionResult(getUniqueId(), "assert-xml-payload-equals-file", allDifferences.size() == 0,
          "Assertion Failure: [assert-xml-payload-equals-file]\n" + diff.toString());
    } catch (SAXException | IOException e) {
      throw new ServiceTestException(e);
    }
  }
}
