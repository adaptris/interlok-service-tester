package com.adaptris.tester.report.junit;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 * Abstract implementations used in {@link JUnitReportTestCase} for storing results.
 *
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 */
public abstract class JUnitReportTestIssueTyped implements JUnitReportTestIssue {

  @XStreamAsAttribute
  private String message;
  @XStreamAsAttribute
  private final String type;

  public JUnitReportTestIssueTyped(String type){
    this.type = type;
  }

  /**
   * Get issue type
   * @return Issue type
   */
  public String getType() {
    return type;
  }

  /**
   * Set issue message
   * @param message Issue message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Get issue message
   * @return Issue message
   */
  public String getMessage(){
    return message;
  }
}
