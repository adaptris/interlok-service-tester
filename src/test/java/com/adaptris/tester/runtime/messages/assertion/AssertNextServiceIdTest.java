package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.messages.TestMessage;

public class AssertNextServiceIdTest extends AssertionCase {

  public AssertNextServiceIdTest(String name) {
    super(name);
  }

  public void testExecute() throws Exception {
    Assertion a = createAssertion();
    TestMessage tm = new TestMessage();
    tm.setNextServiceId("next-service");
    assertTrue(a.execute(tm).isPassed());
    tm.setNextServiceId("not-this-service");
    assertFalse(a.execute(tm).isPassed());
  }

  public void testExpected() throws Exception {
    Assertion a = createAssertion();
    assertEquals("Next service id: next-service", a.expected());
  }

  public void testGetUniqueId() throws Exception {
    Assertion a = createAssertion();
    assertEquals("id", a.getUniqueId());
  }

  public void testGetValue(){
    AssertNextServiceId a = createAssertion();
    assertEquals("next-service", a.getValue());
  }

  public void testShowReturnedMessage(){
    assertTrue(createAssertion().showReturnedMessage());
  }

  @Override
  protected AssertNextServiceId createAssertion() {
    AssertNextServiceId a = new AssertNextServiceId();
    a.setUniqueId("id");
    a.setValue("next-service");
    return a;
  }

}