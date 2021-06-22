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

public class MainSourceTest extends SourceCase {

  @Test
  public void testGetSource() throws Exception {
    final String serviceFile = "service.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    ParentSource fileSource = new FileSource("file:///" + testFile.getAbsolutePath());
    Source source = new MainSource();
    ServiceTestConfig config = new ServiceTestConfig().withSource(fileSource);
    Document document = XmlHelper.createDocument(source.getSource(config), new DocumentBuilderFactoryBuilder());
    assertEquals("service-collection", document.getDocumentElement().getNodeName());
  }

  @Test
  public void testGetSourceNoFiles() throws Exception {
    final String testFile = "service.xml";
    File parentDir = new File(this.getClass().getClassLoader().getResource(testFile).getFile()).getParentFile();
    ParentSource fileSource = new FileSource("file:///" + parentDir.getAbsolutePath() + "/doesnotexist.xml");
    Source source = new MainSource();
    ServiceTestConfig config = new ServiceTestConfig().withSource(fileSource);

    SourceException e = assertThrows(SourceException.class, () -> source.getSource(config));
    assertTrue(e.getMessage().contains("Failed to read file"));
  }

  @Override
  protected Source createSource() {
    return new MainSource();
  }

}
