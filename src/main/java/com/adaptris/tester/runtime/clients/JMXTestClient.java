package com.adaptris.tester.runtime.clients;

import com.adaptris.core.CoreException;
import com.adaptris.core.runtime.AdapterComponentChecker;
import com.adaptris.core.runtime.AdapterComponentCheckerMBean;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.MessageTranslator;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javax.management.*;
import java.io.IOException;
import java.util.Set;

import static com.adaptris.core.runtime.AdapterComponentCheckerMBean.COMPONENT_CHECKER_TYPE;
import static com.adaptris.core.runtime.AdapterComponentMBean.ADAPTER_PREFIX;
import static com.adaptris.core.runtime.AdapterComponentMBean.ID_PREFIX;

/**
 * Abstract class for {@link TestClient} implementations over JMX.
 */
public abstract class JMXTestClient implements TestClient {

  @XStreamOmitField
  private transient AdapterComponentCheckerMBean manager;

  /**
   * Initialises the JMX test client. Test client initialisation includes configuring and connecting to client needed in
   * {@link #applyService(String, TestMessage)}. Method makes a call to {@link #initMBeanServerConnection()} which is
   * abstract method.
   *
   * @throws ServiceTestException wrapping any thrown exception
   */
  @Override
  public final void init() throws ServiceTestException {
    try {
      MBeanServerConnection mBeanServer = initMBeanServerConnection();
      manager = JMX.newMBeanProxy(mBeanServer, createComponentCheckerObjectName(mBeanServer), AdapterComponentCheckerMBean.class);
    } catch (MalformedObjectNameException | IOException | InstanceNotFoundException e) {
      throw new ServiceTestException(e);
    }

  }

  /**
   * Implementations should initialise and return the {@link MBeanServerConnection} to be used in {@link #init()}
   *
   * @return {@link MBeanServerConnection} to be used in {@link #init()}
   * @throws ServiceTestException wrapping any thrown exception
   */
  public abstract MBeanServerConnection initMBeanServerConnection() throws ServiceTestException;

  /**
   * Close connection initialised in {@link #init()}.
   *
   * @throws IOException wrapping any thrown exception (dictated by {@link java.io.Closeable#close()}
   */
  public abstract void close() throws IOException;

  /**
   * Apply the service to the input message and return outputted message, method uses
   * {@link AdapterComponentCheckerMBean#applyService(String, com.adaptris.interlok.types.SerializableMessage)}.
   *
   * @param xml Interlok service configuration
   * @param message Input {@link TestMessage} to test with
   * @return {@link TestMessage} returned by executing service
   * @throws CoreException thrown by {@link AdapterComponentCheckerMBean#applyService(String, com.adaptris.interlok.types.SerializableMessage)}
   */
  @Override
  public final TestMessage applyService(String xml, TestMessage message) throws CoreException {
    MessageTranslator messageTranslator = new MessageTranslator();
    return messageTranslator.translate(manager.applyService(xml, messageTranslator.translate(message), true));
  }

  private ObjectName createComponentCheckerObjectName(MBeanServerConnection mBeanServer) throws MalformedObjectNameException, IOException, InstanceNotFoundException {
    return ObjectName.getInstance(COMPONENT_CHECKER_TYPE + ADAPTER_PREFIX + getAdapterName(mBeanServer) + ID_PREFIX
        + AdapterComponentChecker.class.getSimpleName());
  }

  private String getAdapterName(MBeanServerConnection mBeanServer) throws MalformedObjectNameException, IOException, InstanceNotFoundException {
    return getAdapterObject(mBeanServer).getKeyProperty("id");
  }

  private ObjectName getAdapterObject(MBeanServerConnection mBeanServer) throws MalformedObjectNameException, IOException, InstanceNotFoundException {
    String interlokBaseObject = "com.adaptris:type=Adapter,id=*";
    ObjectName patternName = ObjectName.getInstance(interlokBaseObject);
    Set<ObjectInstance> instances = mBeanServer.queryMBeans(patternName, null);

    if (instances.size() == 0)
      throw new InstanceNotFoundException("No configured Adapters");
    else
      return instances.iterator().next().getObjectName();
  }
}
