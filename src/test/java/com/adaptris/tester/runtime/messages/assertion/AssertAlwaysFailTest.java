package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;

public class AssertAlwaysFailTest extends AssertionCase {

  public AssertAlwaysFailTest(String name) {
    super(name);
  }

  public void testExecute() throws Exception {
    Assertion a = createAssertion();
    AssertionResult result = a.execute(new TestMessage());
    assertFalse(result.isPassed());
  }

  public void testExpected() throws Exception {
    Assertion a = createAssertion();
    assertEquals("This is expected... Will always fail", a.expected());
  }

  public void testGetUniqueId() throws Exception {
    Assertion a = createAssertion();
    assertEquals("id", a.getUniqueId());
  }

  public void testShowReturnedMessage(){
    AssertAlwaysFail a = createAssertion();
    a.setShowReturnedMessage(null);
    assertTrue(a.showReturnedMessage());
    a.setShowReturnedMessage(true);
    assertTrue(a.showReturnedMessage());
    a.setShowReturnedMessage(false);
    assertFalse(a.showReturnedMessage());

  }

  @Override
  protected AssertAlwaysFail createAssertion() {
    AssertAlwaysFail a = new AssertAlwaysFail();
    a.setUniqueId("id");
    a.setShowReturnedMessage(true);
    return a;
  }
}