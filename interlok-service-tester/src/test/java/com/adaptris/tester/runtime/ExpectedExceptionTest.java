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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.adaptris.core.ServiceException;
import com.adaptris.tester.report.junit.JUnitReportTestIssue;
import com.adaptris.tester.report.junit.JUnitReportTestIssueTyped;

public class ExpectedExceptionTest extends TCCase {

  @org.junit.jupiter.api.Test
  public void testDefaultConstructor() {
    ExpectedException e = new ExpectedException();
    assertEquals("com.adaptris.core.ServiceException", e.getClassName());
    assertNull(e.getMessage());
  }

  @org.junit.jupiter.api.Test
  public void testConstructorClass() {
    ExpectedException e = new ExpectedException("com.adaptris.core.OtherException");
    assertEquals("com.adaptris.core.OtherException", e.getClassName());
    assertNull(e.getMessage());
  }

  @org.junit.jupiter.api.Test
  public void testConstructor2Class() {
    ExpectedException e = new ExpectedException("com.adaptris.core.OtherException", "message");
    assertEquals("com.adaptris.core.OtherException", e.getClassName());
    assertEquals("message", e.getMessage());
  }

  @org.junit.jupiter.api.Test
  public void testSetClassName() {
    ExpectedException e = new ExpectedException();
    e.setClassName("com.adaptris.core.OtherException");
    assertEquals("com.adaptris.core.OtherException", e.getClassName());
    assertNull(e.getMessage());
  }

  @org.junit.jupiter.api.Test
  public void testSetMessage() {
    ExpectedException e = new ExpectedException();
    e.setMessage("message");
    assertEquals("com.adaptris.core.ServiceException", e.getClassName());
    assertEquals("message", e.getMessage());
  }


  @org.junit.jupiter.api.Test
  public void testCheckExpected() throws Exception {
    Exception exception = new ServiceException("required-metadata-not-present");
    ExpectedException expectedException = new ExpectedException("com.adaptris.core.ServiceException", "required-metadata-not-present");
    JUnitReportTestIssue result = expectedException.check(exception);
    assertNull(result);
  }

  @org.junit.jupiter.api.Test
  public void testCheckExpectedNoMessage() throws Exception {
    Exception exception = new ServiceException("required-metadata-not-present");
    ExpectedException expectedException = new ExpectedException("com.adaptris.core.ServiceException");
    JUnitReportTestIssue result = expectedException.check(exception);
    assertNull(result);
  }

  @org.junit.jupiter.api.Test
  public void testCheckNotExpected() throws Exception {
    Exception exception = new Exception("required-metadata-not-present");
    ExpectedException expectedException = new ExpectedException("com.adaptris.core.ServiceException", "required-metadata-not-present");
    JUnitReportTestIssueTyped result = expectedException.check(exception);
    assertNotNull(result);
    assertEquals("failure", result.getType());
  }

  @org.junit.jupiter.api.Test
  public void testCheckMessageNotExpected() throws Exception {
    Exception exception = new ServiceException("does not match");
    ExpectedException expectedException = new ExpectedException("com.adaptris.core.ServiceException", "required-metadata-not-present");
    JUnitReportTestIssueTyped result = expectedException.check(exception);
    assertNotNull(result);
    assertEquals("failure", result.getType());
  }

  @org.junit.jupiter.api.Test
  public void testCheckMessageClassNotFound() throws Exception {
    Exception exception = new ServiceException("does not match");
    ExpectedException expectedException = new ExpectedException("com.adaptris.core.OtherException");
    JUnitReportTestIssueTyped result = expectedException.check(exception);
    assertNotNull(result);
    assertEquals("error", result.getType());
  }

  @Override
  protected String createBaseFileName(Object object) {
    return super.createBaseFileName(object) + "-" + ExpectedException.class.getSimpleName();
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    TestCase tc = createBaseTestCase();
    tc.setExpectedException(new ExpectedException("com.adaptris.core.ServiceException", "required-metadata-not-present"));
    return tc;
  }

}
