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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.adaptris.tester.report.junit.JUnitReportFailure;
import com.adaptris.tester.report.junit.JUnitReportTestIssue;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.messages.assertion.AssertMetadataContains;
import com.adaptris.tester.runtime.messages.assertion.AssertPayloadContains;
import com.adaptris.tester.runtime.messages.assertion.Assertion;
import com.adaptris.tester.runtime.messages.assertion.AssertionResult;

public class AssertionsTest extends TCCase {

  @org.junit.jupiter.api.Test
  public void testGetAssertions() throws Exception {
    Assertions a = new Assertions();
    a.addAssertion(new StubAssertion(new AssertionResult("type", true), "message"));

    assertEquals(1, a.size());
    assertEquals("message", a.getAssertions().get(0).expected());
  }

  @org.junit.jupiter.api.Test
  public void testExecutePassed() throws Exception {
    Assertions a = new Assertions();
    a.addAssertion(new StubAssertion(new AssertionResult("type", true), "message"));
    JUnitReportTestIssue issue = a.execute(new TestMessage(), new ServiceTestConfig());

    assertNull(issue);
    assertEquals("message", a.getAssertions().get(0).expected());
  }

  @org.junit.jupiter.api.Test
  public void testExecuteFailed() throws Exception {
    Assertions a = new Assertions();
    a.addAssertion(new StubAssertion(new AssertionResult("type", false), "message-1234"));
    JUnitReportTestIssue issue = a.execute(new TestMessage(), new ServiceTestConfig());

    assertNotNull(issue);
    assertTrue(issue instanceof JUnitReportFailure);
    assertEquals("Assertion Failure: [type]", ((JUnitReportFailure)issue).getMessage());
    assertTrue(((JUnitReportFailure)issue).getText().contains("message-1234"));
    assertEquals("message-1234", a.getAssertions().get(0).expected());
  }

  @org.junit.jupiter.api.Test
  public void testExecutePassedFailed() throws Exception {
    Assertions a = new Assertions();
    a.setAssertions(Arrays.asList(
        new Assertion[] {
            new StubAssertion(new AssertionResult("type", true), "message"),
            new StubAssertion(new AssertionResult("type", false), "message-1234")
        })
        );
    assertEquals(2, a.size());
    JUnitReportTestIssue issue = a.execute(new TestMessage(), new ServiceTestConfig());

    assertNotNull(issue);
    assertTrue(issue instanceof JUnitReportFailure);
    assertEquals("Assertion Failure: [type]", ((JUnitReportFailure)issue).getMessage());
    assertTrue(((JUnitReportFailure)issue).getText().contains("message-1234"));
    assertEquals("message", a.getAssertions().get(0).expected());
  }

  @Override
  protected String createBaseFileName(Object object) {
    return super.createBaseFileName(object) + "-" + Assertions.class.getSimpleName();
  }


  @Override
  protected Object retrieveObjectForSampleConfig() {
    Assertions a = new Assertions();
    Map<String, String> expected = new HashMap<>();
    expected.put("key1", "val1");
    Assertion a1 = new AssertMetadataContains(expected);
    Assertion a2 = new AssertPayloadContains("hello");
    a.addAssertion(a1);
    a.addAssertion(a2);
    TestCase tc = createBaseTestCase();
    tc.setAssertions(a);
    return tc;
  }

  private class StubAssertion implements Assertion{

    private final AssertionResult result;
    private final String message;

    StubAssertion(AssertionResult result, String message) {
      this.result = result;
      this.message = message;
    }

    @Override
    public AssertionResult execute(TestMessage actual, ServiceTestConfig config) {
      return result;
    }

    @Override
    public String expected() {
      return message;
    }

    @Override
    public boolean showReturnedMessage() {
      return false;
    }
  }

}
