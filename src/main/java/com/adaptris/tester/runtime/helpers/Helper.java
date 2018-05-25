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


import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for helper implementation.
 *
 * <p>Helper is concept that allows you to start up service to use during tests, for example: WireMock.</p>
 */
public abstract class Helper implements Closeable {

  @XStreamOmitField
  private Map<String, String> helperProperties;


  public Helper(){
    helperProperties = new HashMap<>();
  }

  /**
   * Implementations initialise service so its usable in tests, all properties for connectivity should also be set
   * using {@link #addHelperProperty(String, String)}.
   * @throws ServiceTestException
   */
  public abstract void init() throws ServiceTestException;

  /**
   * Helper properties should be set during initialisation, they are resolved in the service config.
   *
   * <p>Useful if you use something like the {@link DynamicPortProvider} to set a services port</p>
   *
   * @return Map of helper properties added.
   */
  public Map<String, String> getHelperProperties() {
    return helperProperties;
  }

  /**
   * Adds properties to helper properties, they are resolved in the service config.
   *
   * <p>Useful if you use something like the {@link DynamicPortProvider} to set a services port</p>
   *
   * @param key Helper property key
   * @param value Helper property value
   */
  public void addHelperProperty(String key, String value){
    helperProperties.put(key, value);
  }
}
