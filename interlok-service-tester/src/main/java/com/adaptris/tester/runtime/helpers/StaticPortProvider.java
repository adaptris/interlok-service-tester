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
