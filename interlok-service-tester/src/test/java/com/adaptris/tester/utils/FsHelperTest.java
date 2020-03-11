package com.adaptris.tester.utils;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.File;
import org.junit.Test;
import org.w3c.dom.Document;
import com.adaptris.core.util.DocumentBuilderFactoryBuilder;
import com.adaptris.core.util.XmlHelper;
import com.adaptris.tester.runtime.ServiceTestConfig;

public class FsHelperTest extends FsHelper {

  @Test
  public void testCreateFile_URL() throws Exception {
    final String testFile = "service.xml";
    File parentDir = new File(this.getClass().getClassLoader().getResource(testFile).getFile()).getParentFile();
    ServiceTestConfig config = new ServiceTestConfig().withWorkingDirectory(parentDir);
    File f = createFile("file:///${service.tester.working.directory}/" + testFile, config);
    assertEquals(new File(parentDir, testFile), f);
  }

  @Test
  public void testCreateFile_URLWithSpaces() throws Exception {
    String testFile = "service.xml";
    File parentDir = new File("/home/path/with some/spaces");
    ServiceTestConfig config = new ServiceTestConfig().withWorkingDirectory(parentDir);
    File f = createFile("file:///${service.tester.working.directory}/" + testFile, config);
    assertEquals(new File(parentDir, testFile).getAbsoluteFile(), f);
  }

  @Test
  public void testReadFile() throws Exception {
    final String testFile = "service.xml";
    File parentDir = new File(this.getClass().getClassLoader().getResource(testFile).getFile()).getParentFile();
    ServiceTestConfig config = new ServiceTestConfig().withWorkingDirectory(parentDir);
    byte[] bytes = getFileBytes("file:///${service.tester.working.directory}/" + testFile, config);
    try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
     Document document = XmlHelper.createDocument(in, DocumentBuilderFactoryBuilder.newRestrictedInstance()); 
     assertEquals("service-collection", document.getDocumentElement().getNodeName());
    }
  }
}
