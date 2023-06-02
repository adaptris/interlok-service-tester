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

public class JUnitReportPropertyTest {

  @Test
  public void testGet() throws Exception {
    JUnitReportProperty p = new JUnitReportProperty("name", "value");
    assertEquals("name", p.getName());
    assertEquals("value", p.getValue());
  }

}
