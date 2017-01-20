package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.TestComponent;
import com.adaptris.tester.runtime.messages.TestMessage;

public interface Assertion extends TestComponent {

  void setUniqueId(String uniqueId);

  AssertionResult execute(TestMessage actual) throws ServiceTestException;

  String expected();

  boolean showReturnedMessage();

}
