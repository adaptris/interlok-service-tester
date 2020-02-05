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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StaticPortProviderTest extends PortProviderCase {

  private static final int PORT = 9999;

  @Test
  public void testDefaultPort(){
    PortProvider pp = new StaticPortProvider();
    pp.initPort();
    assertEquals(8080, pp.getPort());
    pp.releasePort();
  }

  @Test
  public void testGetPort(){
    PortProvider pp = createPortProvider();
    pp.initPort();
    assertEquals(PORT, pp.getPort());
    pp.releasePort();
  }

  @Override
  protected PortProvider createPortProvider() {
    StaticPortProvider pp = new StaticPortProvider();
    pp.setPort(PORT);
    return pp;
  }
}
