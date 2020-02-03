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

package com.adaptris.tester.runtime.clients;


import java.io.Closeable;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;

/**
 * Base interface for test client in the service tester.
 */
public interface TestClient extends Closeable {

  /**
   * Initialises the test client.
   * <p>
   * Test client initialisation includes configuring and connecting to client needed in {@link #applyService(String, TestMessage)}.
   * </p>
   * 
   * @return an initialised TestClient instance for try-with-resources...
   * @throws ServiceTestException wrapping any thrown exception
   */
  <T extends TestClient> T init(ServiceTestConfig config) throws ServiceTestException;

  /**
   * Apply the service to the input message and return outputted message.
   *
   * @param xml Interlok service configuration
   * @param message Input {@link TestMessage} to test with
   * @return {@link TestMessage} returned by executing service
   * @throws Exception Exception deliberately thrown so it can be caught during test execution
   */
  TestMessage applyService(String xml, TestMessage message) throws Exception;

}
