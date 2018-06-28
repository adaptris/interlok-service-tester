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

import org.junit.Test;

import java.io.File;

public class VarSubPreprocessorTest extends PreprocessorCase {

  public VarSubPreprocessorTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    String result = createPreprocessor().execute("hello ${foo}");
    assertEquals("hello bar", result);
  }

  @Test
  public void testExecuteNoFiles() throws Exception {
    try {
      new VarSubPreprocessor().execute("hello ${foo}");
      fail();
    } catch (PreprocessorException e){
      assertEquals(e.getMessage(), "At least one properties file must be set");
    }
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    VarSubPreprocessor preprocessor = new VarSubPreprocessor();
    preprocessor.addPropertyFile("file:///home/users/test.properties");
    return preprocessor;
  }

  @Override
  protected Preprocessor createPreprocessor() {
    VarSubPreprocessor preprocessor = new VarSubPreprocessor();
    File propertiesFile = new File(this.getClass().getClassLoader().getResource("test.properties").getFile());
    preprocessor.addPropertyFile("file:///" + propertiesFile.getAbsolutePath());
    return preprocessor;
  }
}
