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

import org.apache.commons.lang.exception.ExceptionUtils;

import com.adaptris.tester.report.junit.JUnitReportError;
import com.adaptris.tester.report.junit.JUnitReportFailure;
import com.adaptris.tester.report.junit.JUnitReportTestIssueTyped;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config expected-exception
 */
@XStreamAlias("expected-exception")
public class ExpectedException {

  private String className;
  private String message;

  public ExpectedException(){
    className = "com.adaptris.core.ServiceException";
    message = null;
  }

  public ExpectedException(String className){
    this.className = className;
    message = null;
  }

  public ExpectedException(String className, String message){
    this.className = className;
    this.message = message;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getClassName() {
    return className;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public JUnitReportTestIssueTyped check(Exception e){
    JUnitReportTestIssueTyped result = null;
    try {
      if (!Class.forName(className).isInstance(e)) {
        return new JUnitReportFailure("Assertion Failure: Expected Exception [" + className + "]", ExceptionUtils.getStackTrace(e));
      }
      if (message != null && !e.getMessage().equals(getMessage())){
        return new JUnitReportFailure("Assertion Failure: Expected Exception [" + className + "] message didn't match [" + message + "]", ExceptionUtils.getStackTrace(e));
      }
    } catch (Exception e1){
      result = new JUnitReportError("Test Error: [" + e1.toString() + "]", ExceptionUtils.getStackTrace(e1));
    }
    return result;
  }
}
