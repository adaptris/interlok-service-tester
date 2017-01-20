package com.adaptris.tester.runtime;

import com.adaptris.core.ServiceException;
import com.adaptris.tester.report.junit.JUnitReportTestIssue;
import com.adaptris.tester.report.junit.JUnitReportTestIssueTyped;

public class ExpectedExceptionTest extends TCCase {

  public ExpectedExceptionTest(String name) {
    super(name);
  }

  public void testDefaultConstructor(){
    ExpectedException e = new ExpectedException();
    assertEquals("com.adaptris.core.ServiceException", e.getClassName());
    assertNull(e.getMessage());
  }

  public void testConstructorClass(){
    ExpectedException e = new ExpectedException("com.adaptris.core.OtherException");
    assertEquals("com.adaptris.core.OtherException", e.getClassName());
    assertNull(e.getMessage());
  }

  public void testConstructor2Class(){
    ExpectedException e = new ExpectedException("com.adaptris.core.OtherException", "message");
    assertEquals("com.adaptris.core.OtherException", e.getClassName());
    assertEquals("message", e.getMessage());
  }

  public void testSetClassName(){
    ExpectedException e = new ExpectedException();
    e.setClassName("com.adaptris.core.OtherException");
    assertEquals("com.adaptris.core.OtherException", e.getClassName());
    assertNull(e.getMessage());
  }

  public void testSetMessage(){
    ExpectedException e = new ExpectedException();
    e.setMessage("message");
    assertEquals("com.adaptris.core.ServiceException", e.getClassName());
    assertEquals("message", e.getMessage());
  }


  @SuppressWarnings("ThrowableInstanceNeverThrown")
  public void testCheckExpected() throws Exception {
    Exception exception = new ServiceException("required-metadata-not-present");
    ExpectedException expectedException = new ExpectedException("com.adaptris.core.ServiceException", "required-metadata-not-present");
    JUnitReportTestIssue result = expectedException.check(exception);
    assertNull(result);
  }

  @SuppressWarnings("ThrowableInstanceNeverThrown")
  public void testCheckExpectedNoMessage() throws Exception {
    Exception exception = new ServiceException("required-metadata-not-present");
    ExpectedException expectedException = new ExpectedException("com.adaptris.core.ServiceException");
    JUnitReportTestIssue result = expectedException.check(exception);
    assertNull(result);
  }

  @SuppressWarnings("ThrowableInstanceNeverThrown")
  public void testCheckNotExpected() throws Exception {
    Exception exception = new Exception("required-metadata-not-present");
    ExpectedException expectedException = new ExpectedException("com.adaptris.core.ServiceException", "required-metadata-not-present");
    JUnitReportTestIssueTyped result = expectedException.check(exception);
    assertNotNull(result);
    assertEquals("failure", result.getType());
  }

  @SuppressWarnings("ThrowableInstanceNeverThrown")
  public void testCheckMessageNotExpected() throws Exception {
    Exception exception = new ServiceException("does not match");
    ExpectedException expectedException = new ExpectedException("com.adaptris.core.ServiceException", "required-metadata-not-present");
    JUnitReportTestIssueTyped result = expectedException.check(exception);
    assertNotNull(result);
    assertEquals("failure", result.getType());
  }

  @SuppressWarnings("ThrowableInstanceNeverThrown")
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