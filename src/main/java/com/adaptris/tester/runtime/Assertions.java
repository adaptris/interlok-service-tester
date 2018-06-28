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

package com.adaptris.tester.runtime;

import com.adaptris.tester.report.junit.JUnitReportFailure;
import com.adaptris.tester.report.junit.JUnitReportTestIssue;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.messages.assertion.Assertion;
import com.adaptris.tester.runtime.messages.assertion.AssertionResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @service-test-config assertions
 */
@XStreamAlias("assertions")
public class Assertions extends AbstractCollection<Assertion> {

  @XStreamImplicit
  private List<Assertion> assertions;

  public Assertions(){
    assertions = new ArrayList<>();
  }

  @Override
  public Iterator<Assertion> iterator() {
    return assertions.listIterator();
  }

  @Override
  public int size() {
    return assertions.size();
  }

  public void setAssertions(List<Assertion> assertion) {
    this.assertions = assertion;
  }

  public List<Assertion> getAssertions() {
    return assertions;
  }

  public void addAssertion(Assertion assertion){
    this.add(assertion);
  }

  public boolean add(Assertion assertion){
    return this.assertions.add(assertion);
  }

  public JUnitReportTestIssue execute(TestMessage returnMessage) throws ServiceTestException{
    JUnitReportTestIssue result = null;
    for(Assertion assertion : getAssertions()) {
      AssertionResult assertionResult = assertion.execute(returnMessage);
      if (!assertionResult.isPassed()) {
        String message = "";
        if (assertion.showReturnedMessage()) {
          message = "Returned\n"
              + returnMessage.toString()
              + "\n\n";
        }
        message = message +
            "Expected\n"
            + assertion.expected();
        result = new JUnitReportFailure(assertionResult.getMessage(),message);
        break;
      }
    }
    return result;
  }
}
