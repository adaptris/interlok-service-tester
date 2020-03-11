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

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.adaptris.tester.runtime.ServiceTestConfig;

public class VarSubPropsPreprocessorTest extends PreprocessorCase {


  @Test
  public void testExecute() throws Exception {
    String result = createPreprocessor().execute("Hello ${foo}", new ServiceTestConfig());
    assertEquals("Hello bar", result);
  }

  @Test
  public void testExecuteWithWorkingDir() throws Exception {
    final String testFile = "service.xml";
    File parentDir = new File(this.getClass().getClassLoader().getResource(testFile).getFile()).getParentFile();
    Map<String, String> properties = new HashMap<>();
    properties.put("foo", "bar");
    properties.put("path", "file:///${service.tester.working.directory}/service.xml");
    VarSubPropsPreprocessor preprocessor = new VarSubPropsPreprocessor(properties);
    String result = preprocessor.execute("Hello ${foo}, The path is: ${path}", new ServiceTestConfig().withWorkingDirectory(parentDir));
    assertEquals("Hello bar, The path is: file:///" + parentDir.getAbsolutePath() + "/service.xml", result);
  }

  @Override
  protected Preprocessor createPreprocessor(){
    Map<String, String> properties = new HashMap<>();
    properties.put("foo", "bar");
    return new VarSubPropsPreprocessor(properties);
  }

}
