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

package com.adaptris.tester.runtime.helpers;

import com.adaptris.tester.utils.PortManager;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Implementation of {@link PortProvider} that returns available port based on offset.
 *
 * <p>
 * Under the covers it uses {@link PortManager}
 * </p>
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
   * Initialises and sets the port, uses {@link PortManager#nextUnusedPort(int)}.
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
   * Release the port, uses {@link PortManager#release(Integer)}.
   */
  @Override
  public void releasePort() {
    PortManager.release(getPort());
  }
}
