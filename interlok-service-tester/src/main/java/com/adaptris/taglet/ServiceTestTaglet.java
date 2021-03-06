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

package com.adaptris.taglet;

import java.util.Map;

import jdk.javadoc.doclet.Taglet;

/**
 * @author mwarman
 */
public class ServiceTestTaglet extends AbstractTaglet {

  @Override
  public String getName() {
    return "service-test-config";
  }

  @Override
  public String getStart() {
    return " <p>In the service test configuration this class is aliased as <b><code>";
  }

  @Override
  public String getEnd() {
    return "</code></b> which is the preferred alternative to the fully qualified classname when building your configuration.</p>";
  }

  public static void register(Map<String, Taglet> tagletMap) {
    ServiceTestTaglet tag = new ServiceTestTaglet();
    Taglet t = tagletMap.get(tag.getName());
    if (t != null) {
      tagletMap.remove(tag.getName());
    }
    tagletMap.put(tag.getName(), tag);
  }

}
