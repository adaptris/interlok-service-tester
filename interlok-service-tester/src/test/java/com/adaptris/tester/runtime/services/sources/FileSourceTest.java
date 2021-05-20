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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.w3c.dom.Document;

import com.adaptris.core.util.DocumentBuilderFactoryBuilder;
import com.adaptris.core.util.XmlHelper;
import com.adaptris.tester.runtime.ServiceTestConfig;

public class FileSourceTest extends SourceCase{

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
    final String testFile = "service.xml";
    File parentDir = new File(this.getClass().getClassLoader().getResource(testFile).getFile()).getParentFile();
    Source source = new FileSource("file:///" + parentDir.getAbsolutePath() + "/doesnotexist.xml");

    SourceException e = assertThrows(SourceException.class, () -> source.getSource(new ServiceTestConfig()));
    assertTrue(e.getMessage().contains("Failed to read file"));
  }

  @Test
  public void testGetSourceWithWorkingDirectory() throws Exception {
    final String testFile = "service.xml";
    File parentDir = new File(this.getClass().getClassLoader().getResource(testFile).getFile()).getParentFile();
    Source source = new FileSource("file:///${service.tester.working.directory}/" + testFile);
    Document document = XmlHelper.createDocument(source.getSource(new ServiceTestConfig().withWorkingDirectory(parentDir)), new DocumentBuilderFactoryBuilder());
    assertEquals("service-collection", document.getDocumentElement().getNodeName());
  }

  @Override
  protected Source createSource() {
    return new FileSource("file:///home/users/service.xml");
  }

}
