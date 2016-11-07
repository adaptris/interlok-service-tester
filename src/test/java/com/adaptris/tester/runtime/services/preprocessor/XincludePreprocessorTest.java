package com.adaptris.tester.runtime.services.preprocessor;

import com.adaptris.core.util.DocumentBuilderFactoryBuilder;
import com.adaptris.core.util.XmlHelper;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.File;

import static org.junit.Assert.*;

public class XincludePreprocessorTest extends PreprocessorCase {

  public XincludePreprocessorTest(String name) {
    super(name);
  }

  @Test
  public void testExecute() throws Exception {
    File serviceXml = new File(this.getClass().getClassLoader().getResource("service.xml").getFile());
    String XML = "<xi:include xmlns:xi=\"http://www.w3.org/2001/XInclude\" href=\"file:///" + serviceXml.getAbsolutePath()  +"\"/>";
    String result = createPreprocessor().execute(XML);
    Document document = XmlHelper.createDocument(result, new DocumentBuilderFactoryBuilder());
    assertEquals("service-collection", document.getDocumentElement().getNodeName());
  }

  @Override
  protected Preprocessor createPreprocessor() {
    return new XincludePreprocessor();
  }
}