package com.adaptris.tester.report.junit;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.Map;

/**
 * Child of {@link JUnitReportTestSuite} for storing results.
 *
 * The intention of class and classes in the hierarchy is to produce JUnit style XML.
 *
 * @junit-config property
 */
@XStreamAlias("property")
public class JUnitReportProperty {

  @XStreamAsAttribute
  private final String name;
  @XStreamAsAttribute
  private final String value;

  public JUnitReportProperty(final String name, final String value){
    this.name = name;
    this.value = value;
  }

  /**
   * Get property name
   * @return property name
   */
  public String getName() {
    return name;
  }

  /**
   * Get property value
   * @return property value
   */
  public String getValue() {
    return value;
  }
}
