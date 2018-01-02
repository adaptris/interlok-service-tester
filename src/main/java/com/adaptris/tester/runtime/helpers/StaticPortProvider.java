package com.adaptris.tester.runtime.helpers;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Implementation of {@link PortProvider} that returns the value set using {@link #setPort(int)} as the port.
 *
 * @service-test-config static-port-provider
 */
@XStreamAlias("static-port-provider")
public class StaticPortProvider implements PortProvider {

  public static final int DEFAULT_PORT = 8080;

  private int port;

  public StaticPortProvider(){
    setPort(DEFAULT_PORT);
  }

  /**
   * Deliberately empty implementation.
   */
  @Override
  public void initPort() {

  }

  /**
   * Returns the port value
   * @return Port Value, default: {@value #DEFAULT_PORT}
   */
  @Override
  public int getPort() {
    return this.port;
  }

  /**
   * Sets the port value
   * @param port Port value
   */
  public void setPort(int port){
    this.port = port;
  }

  /**
   * Deliberately empty implementation.
   */
  @Override
  public void releasePort() {

  }
}
