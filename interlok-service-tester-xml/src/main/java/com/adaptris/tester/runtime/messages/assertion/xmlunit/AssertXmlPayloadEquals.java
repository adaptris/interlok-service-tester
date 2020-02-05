/*
    Copyright 2018 Adaptris Ltd.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.adaptris.tester.runtime.messages.assertion.xmlunit;

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.assertion.AssertionResult;
import com.adaptris.tester.runtime.messages.assertion.PayloadAssertion;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

/**
 * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals {@link #getPayload()}
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 *
 * @service-test-config assert-xml-payload-equals
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
