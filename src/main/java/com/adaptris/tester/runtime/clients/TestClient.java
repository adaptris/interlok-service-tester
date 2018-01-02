package com.adaptris.tester.runtime.clients;


import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;

import java.io.Closeable;

/**
 * Base interface for test client in the service tester.
 */
public interface TestClient extends Closeable {

  /**
   * Initialises the test client. Test client initialisation includes configuring and connecting to client needed in
   * {@link #applyService(String, TestMessage)}.
   *
   * @throws ServiceTestException wrapping any thrown exception
   */
  void init() throws ServiceTestException;

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
