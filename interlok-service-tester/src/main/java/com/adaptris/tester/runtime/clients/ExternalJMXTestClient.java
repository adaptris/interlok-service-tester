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

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

/**
 * Implementation of {@link JMXTestClient} that connects to external JMX URL to be used during testing.
 *
 * @service-test-config external-jmx-test-client
 */
@XStreamAlias("external-jmx-test-client")
public class ExternalJMXTestClient extends JMXTestClient {

  private String jmxUrl;

  @XStreamOmitField
  private JMXConnector jmxConnector;

  /**
   * Initialises connection to external JMX client using {@link #getJmxUrl()}.
   * @return {@link MBeanServerConnection} to be used in {@link #init(ServiceTestConfig config)}
   * @throws ServiceTestException wrapping any thrown exception
   */
  @Override
  public MBeanServerConnection initMBeanServerConnection(ServiceTestConfig config) throws ServiceTestException{
    try {
      jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(getJmxUrl()));
      return jmxConnector.getMBeanServerConnection();
    } catch (IOException e) {
      throw new ServiceTestException(e);
    }
  }

  /**
   * Close JMX connection initialised in {@link #init(ServiceTestConfig config)}.
   *
   * @throws IOException wrapping any thrown exception (dictated by {@link java.io.Closeable#close()}
   */
  @Override
  public void close() throws IOException {
    jmxConnector.close();
  }

  /**
   * Set JMX URL to be used during initialisation.
   *
   * @param jmxUrl JMX URL to be used during initialisation
   */
  public void setJmxUrl(String jmxUrl) {
    this.jmxUrl = jmxUrl;
  }

  /**
   * Get JMX URL to be used during initialisation.
   * @return JMX URL to be used during initialisation
   */
  public String getJmxUrl() {
    return jmxUrl;
  }
}
