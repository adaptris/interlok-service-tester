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

import com.adaptris.core.util.DocumentBuilderFactoryBuilder;
import com.adaptris.core.util.XmlHelper;
import com.adaptris.tester.runtime.ServiceTestConfig;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.File;


public class FileSourceTest extends SourceCase{

  public FileSourceTest(String name) {
    super(name);
  }

  @Test
  public void testGetSource() throws Exception {
    final String serviceFile = "service.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    Source source = new FileSource("file:///" + testFile.getAbsolutePath());
    Document document = XmlHelper.createDocument(source.getSource(new ServiceTestConfig()), new DocumentBuilderFactoryBuilder());
    assertEquals("service-collection", document.getDocumentElement().getNodeName());
  }

  @Test
  public void testGetSourceNoFiles() throws Exception {
    try {
      final String testFile = "service.xml";
      File parentDir = new File(this.getClass().getClassLoader().getResource(testFile).getFile()).getParentFile();
      Source source = new FileSource("file:///" + parentDir.getAbsolutePath() + "/doesnotexist.xml");
      source.getSource(new ServiceTestConfig());
      fail();
    } catch (SourceException e){
      assertTrue(e.getMessage().contains("Failed to read file"));
    }
  }

  @Test
  public void testGetSourceRelative() throws Exception {
    final String serviceFile = "service.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    String relative = testFile.getParentFile().toURI().relativize(testFile.toURI()).getPath();
    Source source = new FileSource("file:///./" + relative);
    Document document = XmlHelper.createDocument(source.getSource(new ServiceTestConfig().withWorkingDirectory(testFile.getParentFile())), new DocumentBuilderFactoryBuilder());
    assertEquals("service-collection", document.getDocumentElement().getNodeName());
  }

  @Override
  protected Source createSource() {
    return new FileSource("file:///home/users/service.xml");
  }
}
