package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;

public class AssertionAlwaysFailTest extends AssertionCase {

  public AssertionAlwaysFailTest(String name) {
    super(name);
  }

  public void testExecute() throws Exception {
    Assertion a = createAssertion();
    AssertionResult result = a.execute(new TestMessage());
    assertFalse(result.isPassed());
  }

  public void testExpected() throws Exception {
    Assertion a = createAssertion();
    assertEquals("Nothing expected... Will always fail", a.expected());
  }

  public void testGetUniqueId() throws Exception {
    Assertion a = createAssertion();
    assertEquals("id", a.getUniqueId());
  }

  @Override
  protected Assertion createAssertion() {
    Assertion a = new AssertionAlwaysFail();
    a.setUniqueId("id");
    return a;
  }
}