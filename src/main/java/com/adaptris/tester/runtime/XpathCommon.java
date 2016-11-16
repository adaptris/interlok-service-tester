package com.adaptris.tester.runtime;

import com.adaptris.annotation.MarshallingCDATA;
import com.adaptris.core.util.DocumentBuilderFactoryBuilder;
import com.adaptris.core.util.XmlHelper;
import com.adaptris.tester.runtime.services.preprocessor.PreprocessorException;
import com.adaptris.util.KeyValuePairSet;
import com.adaptris.util.text.xml.SimpleNamespaceContext;
import com.adaptris.util.text.xml.XPath;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.StringWriter;

public abstract class XpathCommon {

  @MarshallingCDATA
  private String xpath;
  private KeyValuePairSet namespaceContext;

  protected final Node selectSingleNode(Node document, String xpath) throws XpathCommonException {
    try {
      NamespaceContext ctx = SimpleNamespaceContext.create(getNamespaceContext());
      XPath xpathUtils = new XPath(ctx);
      Node node = xpathUtils.selectSingleNode(document, getXpath());
      if (node == null){
        throw new XpathCommonException(String.format("xpath [%s] didn't return a match", xpath));
      }
      return node;
    } catch (XPathExpressionException e) {
      throw new XpathCommonException("Failed to make xpath query", e);
    }
  }

  protected final Node selectSingleNode(String input, String xpath) throws XpathCommonException {
    try {
      return selectSingleNode(XmlHelper.createDocument(input, new DocumentBuilderFactoryBuilder()), xpath);
    } catch (ParserConfigurationException | IOException | SAXException e) {
      throw new XpathCommonException("Failed to make xpath query", e);
    }
  }

  protected final String nodeToString(Node node) throws XpathCommonException {
    StringWriter sw = new StringWriter();
    try {
      Transformer t = TransformerFactory.newInstance().newTransformer();
      t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      t.transform(new DOMSource(node), new StreamResult(sw));
    } catch (TransformerException e) {
      throw new XpathCommonException("Failed to convert node to string", e);
    }
    return sw.toString();
  }

  public String getXpath() {
    return xpath;
  }

  public void setXpath(String xpath) {
    this.xpath = xpath;
  }

  /**
   * @return the namespaceContext
   */
  public KeyValuePairSet getNamespaceContext() {
    return namespaceContext;
  }

  /**
   * Set the namespace context for resolving namespaces.
   * <ul>
   * <li>The key is the namespace prefix</li>
   * <li>The value is the namespace uri</li>
   * </ul>
   *
   * @param kvps the namespace context
   * @see SimpleNamespaceContext#create(KeyValuePairSet)
   */
  public void setNamespaceContext(KeyValuePairSet kvps) {
    this.namespaceContext = kvps;
  }
}
