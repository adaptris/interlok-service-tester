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
