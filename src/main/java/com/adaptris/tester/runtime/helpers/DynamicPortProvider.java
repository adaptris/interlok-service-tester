package com.adaptris.tester.runtime.helpers;

import com.adaptris.core.PortManager;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Implementation of {@link PortProvider} that returns available port based on offset.
 *
 * <p>Under the covers it uses <code>com.adaptris.core.PortManager</code>.</p>
 *
 * @service-test-config dynamic-port-provider
 */
@XStreamAlias("dynamic-port-provider")
public class DynamicPortProvider implements PortProvider {

  public static final int DEFAULT_PORT_OFFSET = 8080;

  private int offset;

  @XStreamOmitField
  private int port;


  public DynamicPortProvider(){
    this(DEFAULT_PORT_OFFSET);
  }

  public DynamicPortProvider(int offset){
    setOffset(offset);
  }


  /**
   * Sets the offset to be used when requesting port.
   * @param offset Offset value.
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * Gets the offset to be used when requesting port.
   * @return Offset value, default: {@value #DEFAULT_PORT_OFFSET}
   */
  public int getOffset() {
    return offset;
  }

  /**
   * Initialises and sets the port, uses <code>com.adaptris.core.PortManager.nextUnusedPort(int)</code>.
   */
  @Override
  public void initPort() {
    setPort(PortManager.nextUnusedPort(getOffset()));
  }

  /**
   * Returns the port set in {@link #initPort()}
   * @return The port
   */
  @Override
  public int getPort() {
    return port;
  }

  private void setPort(int port) {
    this.port = port;
  }

  /**
   * Release the port, uses <code>com.adaptris.core.PortManager.release(int)</code>.
   */
  @Override
  public void releasePort() {
    PortManager.release(getPort());
  }
}
