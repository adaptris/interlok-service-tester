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

package com.adaptris.tester.runtime.services.preprocessor;

import com.adaptris.tester.runtime.ServiceTestConfig;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author mwarman
 */
public class SystemPropertiesPreprocessorTest extends PreprocessorCase {

  @Test
  public void testExecute() throws Exception{
    System.setProperty("foo", "bar");
    String result = createPreprocessor().execute("hello ${foo}", new ServiceTestConfig());
    assertEquals("hello bar", result);
  }

  @Override
  protected Preprocessor createPreprocessor() {
    return new SystemPropertiesPreprocessor();
  }

  @Override
  public boolean isAnnotatedForJunit4() {
    return true;
  }
}
