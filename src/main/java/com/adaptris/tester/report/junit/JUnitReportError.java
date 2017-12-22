package com.adaptris.tester.report.junit;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * Error implementation of {@link JUnitReportTestIssue} used in {@link JUnitReportTestCase} for storing results.
 *
 * <p>The intention of class and classes in the hierarchy is to produce JUnit style XML.</p>
 *
 * <p>NOTE: The difference between failure and error, is a failure meant something expected didn't happen, where as error
 * means something unexpected happened. </p>
 *
 * <p>When tests are executed via {@link com.adaptris.tester.runtime.TestCase#execute(String, com.adaptris.tester.runtime.clients.TestClient, com.adaptris.tester.runtime.services.ServiceToTest)}
 * this class is used to set the test issue using: {@link JUnitReportTestCase#setTestIssue(JUnitReportTestIssue)}.</p>
 *
 * @junit-config error
 */
@XStreamAlias("error")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"text"})
public class JUnitReportError extends JUnitReportTestIssueTyped {

  @XStreamAsAttribute
  @XStreamAlias("type")
  private final static String TYPE = "error";
  private String text;

  /**
   * Constructor sets error message plus calls super constructor {@link JUnitReportTestIssueTyped#JUnitReportTestIssueTyped(String)}
   * with type <code>error</code>.
   * @param message Error message
   */
  public JUnitReportError(String message) {
    super(TYPE);
    setMessage(message);
  }

  /**
   * Constructor sets error text and calls {@link #JUnitReportError(String)} with message.
   * @param message Error message
   * @param text Error text
   */
  public JUnitReportError(String message, String text) {
    this(message);
    this.text = text;
  }

  /**
   * Returns error text (normally stacktrace)
   * @return Error text
   */
  public String getText() {
    return text;
  }
}
