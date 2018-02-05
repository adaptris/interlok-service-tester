package com.adaptris.tester.runtime.services.preprocessor;

import com.adaptris.core.util.DocumentBuilderFactoryBuilder;
import com.adaptris.core.util.XmlHelper;
import com.adaptris.tester.runtime.XpathCommon;
import com.adaptris.tester.runtime.XpathCommonException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 *
 * @service-test-config remove-node-preprocessor
 */
@XStreamAlias("remove-node-preprocessor")
public class RemoveNodePreprocessor extends XpathCommon implements Preprocessor {

  @Override
  public String execute(String input) throws PreprocessorException {
    try {
      Node document = XmlHelper.createDocument(input, new DocumentBuilderFactoryBuilder());
      Node node =  selectSingleNode(document);
      node.getParentNode().removeChild(node);
      return nodeToString(document);
    } catch (ParserConfigurationException | SAXException | IOException | XpathCommonException e) {
      throw new PreprocessorException("Failed to remove node", e);
    }
  }
}
