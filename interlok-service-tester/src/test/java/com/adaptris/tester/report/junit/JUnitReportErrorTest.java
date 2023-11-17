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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnitReportErrorTest {

  private final static String MESSAGE  = "An error occurred";
  private final static String TEXT  = "Details about the error.";

  @Test
  public void testGetType() throws Exception {
    JUnitReportError j = createReportError();
    assertEquals("error", j.getType());
  }

  @Test
  public void testGetMessage() throws Exception {
    JUnitReportError j = createReportError();
    assertEquals(MESSAGE, j.getMessage());
  }

  @Test
  public void testGetText() throws Exception {
    JUnitReportError j = createReportError();
    assertEquals(TEXT, j.getText());
  }

  private JUnitReportError createReportError(){
    return new JUnitReportError(MESSAGE, TEXT);
  }
}
