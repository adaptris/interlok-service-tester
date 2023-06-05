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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

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
