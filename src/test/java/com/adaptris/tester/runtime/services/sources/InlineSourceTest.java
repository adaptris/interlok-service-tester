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

public class InlineSourceTest extends SourceCase {
  public InlineSourceTest(String name) {
    super(name);
  }

  @Test
  public void testGetSource() throws Exception {
    Source source = createSource();
    Document document = XmlHelper.createDocument(source.getSource(new ServiceTestConfig()), new DocumentBuilderFactoryBuilder());
    assertEquals("add-metadata-service", document.getDocumentElement().getNodeName());
  }

  @Override
  protected Source createSource() {
    InlineSource source = new InlineSource();
    source.setXml(
        "\n" +
            "<add-metadata-service>\n" +
            "  <unique-id>Add2</unique-id>\n" +
            "  <metadata-element>\n" +
            "    <key>key2</key>\n" +
            "    <value>val2</value>\n" +
            "  </metadata-element>\n" +
            "</add-metadata-service>\n" +
            ""
    );
    return source;
  }
}
