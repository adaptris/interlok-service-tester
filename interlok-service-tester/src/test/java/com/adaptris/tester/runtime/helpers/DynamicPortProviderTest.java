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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DynamicPortProviderTest extends PortProviderCase {

  private static final int PORT = 8080;

  @Test
  public void testDefaultOffset(){
    DynamicPortProvider pp = new DynamicPortProvider();
    pp.initPort();
    assertEquals(8080, pp.getOffset());
    assertTrue(8080 <= pp.getPort());
    pp.releasePort();
  }

  @Test
  public void testGetPort(){
    DynamicPortProvider pp = createPortProvider();
    pp.initPort();
    assertEquals(PORT, pp.getOffset());
    assertTrue(PORT <= pp.getPort());
    pp.releasePort();
  }

  @Override
  protected DynamicPortProvider createPortProvider() {
    DynamicPortProvider pp = new DynamicPortProvider();
    pp.setOffset(PORT);
    return pp;
  }

}
