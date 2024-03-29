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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitReportTestCaseTest{

  private static final String NAME = "testcase";

  @Test
  public void testIsFailure() throws Exception {
    JUnitReportTestCase tc = new JUnitReportTestCase(NAME);
    tc.setTestIssue(new JUnitReportFailure("Failed"));
    assertTrue(tc.isFailure());
    assertFalse(tc.isError());
    assertFalse(tc.isSkipped());
    assertEquals(1, tc.getIssue().size());
    assertTrue(tc.getIssue().get(0) instanceof JUnitReportFailure);
    assertEquals("Failed", ((JUnitReportFailure)tc.getIssue().get(0)).getMessage());
  }

  @Test
  public void testIsError() throws Exception {
    JUnitReportTestCase tc = new JUnitReportTestCase(NAME);
    tc.setTestIssue(new JUnitReportError("Error"));
    assertTrue(tc.isError());
    assertFalse(tc.isFailure());
    assertFalse(tc.isSkipped());
    assertEquals(1, tc.getIssue().size());
    assertTrue(tc.getIssue().get(0) instanceof JUnitReportError);
    assertEquals("Error", ((JUnitReportError)tc.getIssue().get(0)).getMessage());
  }

  @Test
  public void testIsSkipped() throws Exception {
    JUnitReportTestCase tc = new JUnitReportTestCase(NAME);
    tc.setTestIssue(new JUnitReportSkipped());
    assertTrue(tc.isSkipped());
    assertFalse(tc.isError());
    assertFalse(tc.isFailure());
    assertEquals(1, tc.getIssue().size());
    assertTrue(tc.getIssue().get(0) instanceof JUnitReportSkipped);
  }

  @Test
  public void testIsPassed() throws Exception {
    JUnitReportTestCase tc = new JUnitReportTestCase(NAME);
    tc.setTestIssue(null);
    assertFalse(tc.isSkipped());
    assertFalse(tc.isError());
    assertFalse(tc.isFailure());
    assertNull(tc.getIssue());
  }

  @Test
  public void testGetName() throws Exception {
    JUnitReportTestCase tc = new JUnitReportTestCase(NAME);
    assertEquals(NAME, tc.getName());
  }

  @Test
  public void testGetTime() throws Exception {
    JUnitReportTestCase tc = new JUnitReportTestCase(NAME);
    tc.setTime(10);
    assertEquals(10, tc.getTime(), 0.0);
  }

  @Test
  public void testGetClassname() throws Exception {
    JUnitReportTestCase tc = new JUnitReportTestCase(NAME);
    tc.setClassname("classname");
    assertEquals("classname", tc.getClassname());
  }

}
