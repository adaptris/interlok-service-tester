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

package com.adaptris.tester.report.junit;

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.clients.TestClient;
import com.adaptris.tester.runtime.services.ServiceToTest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * Failure implementation of {@link JUnitReportTestIssue} used in {@link JUnitReportTestCase} for storing results.
 *
 * <p>
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 * </p>
 *
 * <p>
 * NOTE: The difference between failure and error, is a failure meant something expected didn't happen, where as error means
 * something unexpected happened.
 * </p>
 *
 * <p>
 * When tests are executed via
 * {@link com.adaptris.tester.runtime.TestCase#execute(String, TestClient, ServiceToTest, ServiceTestConfig)} this class is used to
 * set the test issue if applicable using: {@link JUnitReportTestCase#setTestIssue(JUnitReportTestIssue)}.
 * </p>
 *
 * @junit-config failure
 */
@XStreamAlias("failure")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"text"})
public class JUnitReportFailure extends JUnitReportTestIssueTyped {

  @XStreamAsAttribute
  @XStreamAlias("type")
  private final static String TYPE = "failure";
  private String text;

  /**
   * Constructor sets error message plus calls super constructor {@link JUnitReportTestIssueTyped#JUnitReportTestIssueTyped(String)}
   * with type <code>failure</code>.
   * @param message Error message
   */
  public JUnitReportFailure(String message) {
    super(TYPE);
    setMessage(message);
  }

  /**
   * Constructor sets failure text and calls {@link #JUnitReportFailure(String)} with message.
   * @param message Error message
   * @param text Error text
   */
  public JUnitReportFailure(String message, String text) {
    this(message);
    this.text = text;
  }

  /**
   * Returns Failure text (normally stacktrace)
   * @return Failure text
   */
  public String getText() {
    return text;
  }
}
