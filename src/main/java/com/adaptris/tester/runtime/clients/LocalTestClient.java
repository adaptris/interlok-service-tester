package com.adaptris.tester.runtime.clients;

import com.adaptris.core.util.JmxHelper;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.management.MBeanServerConnection;
import java.io.IOException;

/**
 * Implementation of {@link JMXTestClient} that uses  {@link JmxHelper#findMBeanServer()} to get test client.
 *
 * @service-test-config embedded-jmx-test-client
 */
@XStreamAlias("local-jmx-test-client")
public class LocalTestClient extends JMXTestClient {

  /**
   * Returns {@link MBeanServerConnection} using {@link JmxHelper#findMBeanServer()}.
   *
   * @return {@link MBeanServerConnection} to be used in {@link #init()}
   */
  @Override
  public MBeanServerConnection initMBeanServerConnection() {
    return JmxHelper.findMBeanServer();
  }

  /**
   * Empty implementation
   * @throws IOException wrapping any thrown exception (dictated by {@link java.io.Closeable#close()}
   */
  @Override
  public void close() {
  }
}
