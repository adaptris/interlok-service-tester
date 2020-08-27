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

package com.adaptris.tester.runtime;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.adaptris.annotation.AutoPopulated;
import com.adaptris.annotation.MarshallingCDATA;
import com.adaptris.core.util.DocumentBuilderFactoryBuilder;
import com.adaptris.core.util.XmlHelper;
import com.adaptris.util.KeyValuePairSet;
import com.adaptris.util.text.xml.SimpleNamespaceContext;
import com.adaptris.util.text.xml.XPath;

/**
 * Abstract class providing common functionality and configuration to its extenders.
 */
public class XpathCommon {

  @MarshallingCDATA
  private String xpath;
  @AutoPopulated
  private KeyValuePairSet namespaceContext;

  public XpathCommon() {
    setNamespaceContext(new KeyValuePairSet());
  }

  /**
   * Returns result of {@link #getXpath()} from input parameter.
   *
   * <p><b>Example:</b></p>
   * <p>Payload:<br />
   * {@code <root><key>value</key></root> }
   * </p>
   * <p>Xpath:<br />
   * {@code /root/key}
   * </p>
   * <p>Node:<br />
   * {@code value}
   * </p>
   * @param document Xml to run xpath against
   * @return resulting node from xpath query
   * @throws XpathCommonException wraps thrown exceptions
   */
  protected final Node selectSingleNode(Node document) throws XpathCommonException {
    try {
      NamespaceContext ctx = SimpleNamespaceContext.create(getNamespaceContext());
      XPath xpathUtils = new XPath(ctx);
      Node node = xpathUtils.selectSingleNode(document, getXpath());
      if (node == null){
        throw new XpathCommonException(String.format("xpath [%s] didn't return a match", getXpath()));
      }
      return node;
    } catch (XPathExpressionException e) {
      throw new XpathCommonException("Failed to make xpath query", e);
    }
  }

  /**
   * Convenience method that calls {@link #selectSingleNode(Node)} after turning string into node.
   *
   * @param input Xml to run xpath against
   * @return resulting node from xpath query
   * @throws XpathCommonException wraps thrown exceptions
   */
  public final Node selectSingleNode(String input) throws XpathCommonException {
    try {
      return selectSingleNode(XmlHelper.createDocument(input, new DocumentBuilderFactoryBuilder()));
    } catch (ParserConfigurationException | IOException | SAXException e) {
      throw new XpathCommonException("Failed to make xpath query", e);
    }
  }

  /**
   * Utility class that returns a String of a Node
   * @param node node to transform
   * @return string of node
   * @throws XpathCommonException wraps thrown exceptions
   */
  public final String nodeToString(Node node) throws XpathCommonException {
    StringWriter sw = new StringWriter();
    try {
      Transformer t = TransformerFactory.newInstance().newTransformer();
      t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      DOMSource source = new DOMSource(node);
      if (source.getNode().getNodeType() != Node.ATTRIBUTE_NODE) {
        t.transform(source, new StreamResult(sw));
      } else {
        sw.write(source.getNode().toString());
      }
    } catch (TransformerException e) {
      throw new XpathCommonException("Failed to convert node to string", e);
    }
    return sw.toString();
  }

  /**
   * Returns boolean result of {@link #getXpath()} against input param.
   *
   * <p><b>Example:</b></p>
   * <p>Payload:<br />
   * {@code <root><key>value</key></root> }
   * </p>
   * <p>Xpath:<br />
   * {@code count(/root/key) = 1}
   * </p>
   *
   * @param input Xml to run xpath against
   * @return Boolean result of xpath
   * @throws XpathCommonException wraps thrown exceptions
   */
  protected final boolean selectSingleBoolean(String input) throws XpathCommonException {
    javax.xml.xpath.XPath xpath = com.adaptris.util.text.xml.XPath.newXPathFactory().newXPath();
    NamespaceContext ctx = SimpleNamespaceContext.create(getNamespaceContext());
    if(ctx != null) {
      xpath.setNamespaceContext(ctx);
    }
    try {
      XPathExpression xpr = xpath.compile(getXpath());
      return (Boolean)xpr.evaluate(XmlHelper.createDocument(input, new DocumentBuilderFactoryBuilder()), XPathConstants.BOOLEAN);
    } catch (XPathExpressionException | SAXException | ParserConfigurationException | IOException e) {
      throw new XpathCommonException("Failed to execute xpath", e);
    }
  }

  /**
   * Returns the xpath to use.
   * @return the xpath
   */
  public String getXpath() {
    return xpath;
  }

  /**
   * Sets the xpath to use.
   * @param xpath the xpath
   */
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
    namespaceContext = kvps;
  }
}
