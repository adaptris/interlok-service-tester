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
 * Removes node from XML at {@link #getXpath()}.
 *
 * @service-test-config remove-node-preprocessor
 */
@XStreamAlias("remove-node-preprocessor")
public class RemoveNodePreprocessor extends XpathCommon implements Preprocessor {

  /**
   * {@inheritDoc}
   */
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
