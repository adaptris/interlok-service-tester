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

package com.adaptris.tester.runtime.services.sources;

import com.adaptris.annotation.ComponentProfile;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config main-source
 */
@XStreamAlias("main-source")
@ComponentProfile(since = "4.1.0", summary = "Use the source defined in ServiceTest")
public class MainSource implements Source {

  @Override
  public String getSource(ServiceTestConfig config) throws SourceException {
    return config.source.getSource(config);
  }

}
