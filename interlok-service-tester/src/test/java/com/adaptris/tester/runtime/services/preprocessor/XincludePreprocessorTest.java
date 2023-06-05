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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import com.adaptris.core.util.DocumentBuilderFactoryBuilder;
import com.adaptris.core.util.XmlHelper;
import com.adaptris.tester.runtime.ServiceTestConfig;

public class XincludePreprocessorTest extends PreprocessorCase {

  @Test
  public void testExecute() throws Exception {
    File serviceXml = new File(this.getClass().getClassLoader().getResource("service.xml").getFile());
    String XML = "<xi:include xmlns:xi=\"http://www.w3.org/2001/XInclude\" href=\"file:///" + serviceXml.getAbsolutePath()  +"\"/>";
    String result = createPreprocessor().execute(XML, new ServiceTestConfig());
    Document document = XmlHelper.createDocument(result, new DocumentBuilderFactoryBuilder());
    assertEquals("service-collection", document.getDocumentElement().getNodeName());
  }

  @Test
  public void testExecuteNoFile() throws Exception {
    try {
      final String testFile = "service.xml";
      File parentDir = new File(this.getClass().getClassLoader().getResource(testFile).getFile()).getParentFile();
      String XML = "<xi:include xmlns:xi=\"http://www.w3.org/2001/XInclude\" href=\"file:///" + parentDir.getAbsolutePath() + "/doesnotexist.xml\"/>";
      createPreprocessor().execute(XML, new ServiceTestConfig());
      fail();
    } catch (PreprocessorException e){
      assertTrue(e.getMessage().contains("Failed to perform xinclude"));
    }
  }

  @Override
  protected Preprocessor createPreprocessor() {
    return new XincludePreprocessor();
  }

}
