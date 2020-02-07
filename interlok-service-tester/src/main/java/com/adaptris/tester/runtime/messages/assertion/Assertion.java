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

package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.tester.runtime.ServiceTestConfig;
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
   * 
   * @param uniqueId The unique id
   * @implNote The default implementation is no-op
   * @deprecated since 3.10 with no replacement since it adds no value.
   */
  @Deprecated
  default void setUniqueId(String uniqueId) {
    
  }

  /**
   * Default method since setUniqueID is deprecated
   * 
   * @implNote The default implementation just returns null
   */
  @Override
  default String getUniqueId() {
    return null;
  }

  /**
   * Execute assertion against test message.
   * @param actual Message resulting from text execution
   * @param config Service test config
   * @return Return result of assertion using {@link AssertionResult}
   * @throws ServiceTestException wraps thrown exceptions
   */
  AssertionResult execute(TestMessage actual, ServiceTestConfig config) throws ServiceTestException;

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
