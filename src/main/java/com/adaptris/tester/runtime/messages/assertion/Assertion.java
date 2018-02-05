package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.TestComponent;
import com.adaptris.tester.runtime.messages.TestMessage;

/**
 * Base interface for assertions.
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 */
public interface Assertion extends TestComponent {

  /**
   * Sets the unique id
   * @param uniqueId The unique id
   */
  void setUniqueId(String uniqueId);

  /**
   * Execute assertion against test message.
   * @param actual Message resulting from text execution
   * @return Return result of assertion using {@link AssertionResult}
   * @throws ServiceTestException wraps thrown exceptions
   */
  AssertionResult execute(TestMessage actual) throws ServiceTestException;

  /**
   * Returns expected result of assertion. Used in error reporting.
   * @return Expected result of assertion.
   */
  String expected();

  /**
   * Controls whether returned message should be included in error text.
   * @return Whether returned message should be included in error text.
   */
  boolean showReturnedMessage();

}
