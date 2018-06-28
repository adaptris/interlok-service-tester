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
