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

import com.adaptris.core.Adapter;
import com.adaptris.core.CoreException;
import com.adaptris.core.SharedComponentList;
import com.adaptris.core.runtime.AdapterManager;
import com.adaptris.core.util.JmxHelper;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.util.GuidGenerator;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import java.io.IOException;

/**
 * Implementation of {@link JMXTestClient} that creates an embedded version of the {@link Adapter} to be used during testing.
 *
 * @service-test-config embedded-jmx-test-client
 */
@XStreamAlias("embedded-jmx-test-client")
public class EmbeddedTestClient extends JMXTestClient {

  @XStreamOmitField
  private transient AdapterManager adapterManager;

  private SharedComponentList sharedComponents;

  public EmbeddedTestClient(){
    setSharedComponents(new SharedComponentList());
  }

  /**
   * Initialises an {@link Adapter} using the {@link AdapterManager}, shared components can be added using {@link #setSharedComponents(SharedComponentList)}.
   *
   * @return {@link MBeanServerConnection} to be used in {@link #init()}
   * @throws ServiceTestException wrapping any thrown exception
   */
  @Override
  public MBeanServerConnection initMBeanServerConnection() throws ServiceTestException {
    try {
      Adapter adapter = new Adapter();
      adapter.setSharedComponents(getSharedComponents());
      GuidGenerator guidGenerator = new GuidGenerator();
      adapter.setUniqueId(guidGenerator.getUUID());
      adapterManager = new AdapterManager(adapter);
      adapterManager.registerMBean();
      adapterManager.requestStart();
      return JmxHelper.findMBeanServer();
    } catch (CoreException | MalformedObjectNameException e) {
      throw new ServiceTestException(e);
    }
  }


  /**
   * Closes the {@link Adapter} using the {@link AdapterManager},
   * @throws IOException wrapping any thrown exception (dictated by {@link java.io.Closeable#close()}
   */
  @Override
  public void close() throws IOException {
    try {
      adapterManager.requestClose();
      adapterManager.unregisterMBean();
    } catch (CoreException e) {
      throw new IOException(e);
    }
  }

  /**
   * Set {@link SharedComponentList} which is added to the {@link Adapter} during initialisation.
   *
   * @param sharedComponentList {@link SharedComponentList} which is added to the {@link Adapter}
   */
  public void setSharedComponents(SharedComponentList sharedComponentList) {
    this.sharedComponents = sharedComponentList;
  }

  /**
   * Returns {@link SharedComponentList} which is added to the {@link Adapter} during initialisation.
   * @return {@link SharedComponentList} that has been set.
   */
  public SharedComponentList getSharedComponents() {
    return sharedComponents;
  }
}
