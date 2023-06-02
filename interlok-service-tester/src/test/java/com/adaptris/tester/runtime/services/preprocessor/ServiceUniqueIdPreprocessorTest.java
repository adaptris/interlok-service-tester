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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.adaptris.tester.runtime.ServiceTestConfig;

/**
 * @author mwarman
 */
public class ServiceUniqueIdPreprocessorTest extends PreprocessorCase {

  private static final String ADAPTER_XML = "<adapter><channel-list><channel> <unique-id>channel-1</unique-id><workflow-list><standard-workflow><unique-id>workflow-1</unique-id><service-collection class=\"service-list\"><unique-id>naughty-liskov</unique-id><services><service-list><unique-id>service-1</unique-id><services><add-metadata-service><unique-id>add-metadata</unique-id><metadata-element><key>key1</key><value>value1</value></metadata-element></add-metadata-service><service-list><unique-id>service-2</unique-id><services><add-metadata-service><unique-id>add-metadata</unique-id><metadata-element><key>key2</key><value>value2</value></metadata-element></add-metadata-service></services></service-list></services></service-list></services></service-collection></standard-workflow></workflow-list></channel></channel-list></adapter>";
  private static final String EXPECTED_SINGLE = "<service-list><unique-id>service-1</unique-id><services><add-metadata-service><unique-id>add-metadata</unique-id><metadata-element><key>key1</key><value>value1</value></metadata-element></add-metadata-service><service-list><unique-id>service-2</unique-id><services><add-metadata-service><unique-id>add-metadata</unique-id><metadata-element><key>key2</key><value>value2</value></metadata-element></add-metadata-service></services></service-list></services></service-list>";
  private static final String EXPECTED_SINGLE_ADD = "<add-metadata-service><unique-id>add-metadata</unique-id><metadata-element><key>key1</key><value>value1</value></metadata-element></add-metadata-service>";
  private static final String EXPECTED_MULTI = "<service-list><unique-id>service-2</unique-id><services><add-metadata-service><unique-id>add-metadata</unique-id><metadata-element><key>key2</key><value>value2</value></metadata-element></add-metadata-service></services></service-list>";
  private static final String EXPECTED_MULTI_ADD = "<add-metadata-service><unique-id>add-metadata</unique-id><metadata-element><key>key2</key><value>value2</value></metadata-element></add-metadata-service>";

  private static final String CHANNEL_WORKFLOW_SINGLE_SERVICE_XPATH = "//channel-list/*[unique-id = 'channel-1']/workflow-list/*[unique-id = 'workflow-1']/service-collection/services/*[unique-id = 'service-1']";
  private static final String CHANNEL_WORKFLOW_MULTI_SERVICE_XPATH = CHANNEL_WORKFLOW_SINGLE_SERVICE_XPATH + "/*[self::services or self::case or self::then or self::otherwise]/*[unique-id = 'service-2']";

  private static final String WORKFLOW_SINGLE_SERVICE_XPATH = "//workflow-list/*[unique-id = 'workflow-1']/service-collection/services/*[unique-id = 'service-1']";
  private static final String WORKFLOW_MULTI_SERVICE_XPATH = WORKFLOW_SINGLE_SERVICE_XPATH + "/*[self::services or self::case or self::then or self::otherwise]/*[unique-id = 'service-2']";

  private static final String SINGLE_SERVICE_XPATH = "//*[unique-id = 'service-1']";
  private static final String MULTI_SERVICE_XPATH = SINGLE_SERVICE_XPATH + "/*[self::services or self::case or self::then or self::otherwise]/*[unique-id = 'service-2']";

  private static final String ADAPTER_XML_WITH_CONDITION = "<adapter><channel-list><channel><unique-id>channel</unique-id><workflow-list><standard-workflow><unique-id>workflow</unique-id><service-collection class=\"service-list\"><unique-id>serene-fermi</unique-id><services><switch><unique-id>switch</unique-id><case><condition class=\"metadata\"><operator class=\"metadata-ends-with-ignore-case\"><result-key>comparison-result-json</result-key><value>.json</value><ignore-case>true</ignore-case></operator><metadata-key>originalname</metadata-key></condition><service class=\"service-list\"><unique-id>json-case</unique-id><services><logging-service><unique-id>log-json</unique-id><log-level>DEBUG</log-level><text>Processing JSON file</text></logging-service><add-metadata-service><unique-id>set-metadata-action-json</unique-id><metadata-element><key>action</key><value>json</value></metadata-element></add-metadata-service></services></service></case><case><condition class=\"metadata\"><operator class=\"metadata-ends-with-ignore-case\"><result-key>comparison-result-xml</result-key><value>.xml</value><ignore-case>true</ignore-case></operator><metadata-key>originalname</metadata-key></condition><service class=\"service-list\"><unique-id>xml-case</unique-id><services><logging-service><unique-id>log-xml</unique-id><log-level>DEBUG</log-level><text>Processing XML file</text></logging-service><add-metadata-service><unique-id>set-metadata-action-xml</unique-id><metadata-element><key>action</key><value>xml</value></metadata-element></add-metadata-service></services></service></case><case><condition class=\"case-default\"/><service class=\"service-list\"><unique-id>default-case</unique-id><services><logging-service><unique-id>log-file</unique-id><log-level>DEBUG</log-level><text>Processing unknown file</text></logging-service><add-metadata-service><unique-id>set-metadata-action-default</unique-id><metadata-element><key>action</key><value>default</value></metadata-element></add-metadata-service></services></service></case></switch></services></service-collection></standard-workflow></workflow-list></channel></channel-list></adapter>";
  private static final String SERVICE_IN_CONDITION_1 = "<service class=\"service-list\"><unique-id>json-case</unique-id><services><logging-service><unique-id>log-json</unique-id><log-level>DEBUG</log-level><text>Processing JSON file</text></logging-service><add-metadata-service><unique-id>set-metadata-action-json</unique-id><metadata-element><key>action</key><value>json</value></metadata-element></add-metadata-service></services></service>";
  private static final String SERVICE_IN_CONDITION_2 = "<service class=\"service-list\"><unique-id>xml-case</unique-id><services><logging-service><unique-id>log-xml</unique-id><log-level>DEBUG</log-level><text>Processing XML file</text></logging-service><add-metadata-service><unique-id>set-metadata-action-xml</unique-id><metadata-element><key>action</key><value>xml</value></metadata-element></add-metadata-service></services></service>";

  private static final String ADAPTER_XML_WITH_IF_THEN = "<adapter><channel-list><channel><unique-id>channel</unique-id><workflow-list><standard-workflow><unique-id>workflow</unique-id><service-collection class=\"service-list\"><unique-id>serene-fermi</unique-id><services><if-then-otherwise><unique-id>if-else</unique-id><condition class=\"expression\"><algorithm>%message{myKey} == 1</algorithm></condition><then><logging-service><unique-id>log-if</unique-id><log-level>DEBUG</log-level><text>value of keyKey is 1</text></logging-service></then><otherwise><logging-service><unique-id>log-else</unique-id><log-level>DEBUG</log-level><text>value of keyKey is not 1</text></logging-service></otherwise></if-then-otherwise></services></service-collection></standard-workflow></workflow-list></channel></channel-list></adapter>";
  private static final String EXPECTED_SERVICE_IN_IF = "<logging-service><unique-id>log-if</unique-id><log-level>DEBUG</log-level><text>value of keyKey is 1</text></logging-service>";
  private static final String EXPECTED_SERVICE_IN_ELSE = "<logging-service><unique-id>log-else</unique-id><log-level>DEBUG</log-level><text>value of keyKey is not 1</text></logging-service>";

  @Test
  public void testExecute() throws Exception{
    ServiceUniqueIdPreprocessor preprocessor;
    preprocessor = new ServiceUniqueIdPreprocessor("channel-1", "workflow-1", Collections.singletonList("service-1"));
    assertEquals(EXPECTED_SINGLE, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));
    preprocessor = new ServiceUniqueIdPreprocessor("workflow-1", Collections.singletonList("service-1"));
    assertEquals(EXPECTED_SINGLE, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));
    preprocessor = new ServiceUniqueIdPreprocessor(Collections.singletonList("service-1"));
    assertEquals(EXPECTED_SINGLE, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));

    preprocessor = new ServiceUniqueIdPreprocessor("channel-1", "workflow-1", Arrays.asList("service-1", "add-metadata"));
    assertEquals(EXPECTED_SINGLE_ADD, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));
    preprocessor = new ServiceUniqueIdPreprocessor("workflow-1", Arrays.asList("service-1", "add-metadata"));
    assertEquals(EXPECTED_SINGLE_ADD, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));
    preprocessor = new ServiceUniqueIdPreprocessor(Arrays.asList("service-1", "add-metadata"));
    assertEquals(EXPECTED_SINGLE_ADD, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));

    preprocessor = new ServiceUniqueIdPreprocessor("channel-1", "workflow-1", Arrays.asList("service-1", "service-2"));
    assertEquals(EXPECTED_MULTI, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));
    preprocessor = new ServiceUniqueIdPreprocessor("workflow-1", Arrays.asList("service-1", "service-2"));
    assertEquals(EXPECTED_MULTI, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));
    preprocessor = new ServiceUniqueIdPreprocessor(Arrays.asList("service-1", "service-2"));
    assertEquals(EXPECTED_MULTI, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));


    preprocessor = new ServiceUniqueIdPreprocessor("channel-1", "workflow-1", Arrays.asList("service-1", "service-2", "add-metadata"));
    assertEquals(EXPECTED_MULTI_ADD, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));
    preprocessor = new ServiceUniqueIdPreprocessor( "workflow-1", Arrays.asList("service-1", "service-2", "add-metadata"));
    assertEquals(EXPECTED_MULTI_ADD, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));
    preprocessor = new ServiceUniqueIdPreprocessor(Arrays.asList("service-1", "service-2", "add-metadata"));
    assertEquals(EXPECTED_MULTI_ADD, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));

    //Shows it works and returns first
    preprocessor = new ServiceUniqueIdPreprocessor(Collections.singletonList("add-metadata"));
    assertEquals(EXPECTED_SINGLE_ADD, preprocessor.execute(ADAPTER_XML, new ServiceTestConfig()));
  }

  @Test
  public void testServiceInCondition() throws Exception {
    ServiceUniqueIdPreprocessor preprocessor = new ServiceUniqueIdPreprocessor("channel", "workflow", Arrays.asList("switch", "json-case"));
    assertEquals(SERVICE_IN_CONDITION_1, preprocessor.execute(ADAPTER_XML_WITH_CONDITION, new ServiceTestConfig()));

    preprocessor = new ServiceUniqueIdPreprocessor(Arrays.asList("switch", "xml-case"));
    assertEquals(SERVICE_IN_CONDITION_2, preprocessor.execute(ADAPTER_XML_WITH_CONDITION, new ServiceTestConfig()));
  }

  @Test
  public void testServiceInIfElse() throws Exception {
    ServiceUniqueIdPreprocessor preprocessor = new ServiceUniqueIdPreprocessor("channel", "workflow", Arrays.asList("if-else", "log-if"));
    assertEquals(EXPECTED_SERVICE_IN_IF, preprocessor.execute(ADAPTER_XML_WITH_IF_THEN, new ServiceTestConfig()));

    preprocessor = new ServiceUniqueIdPreprocessor("channel", "workflow", Arrays.asList("if-else", "log-else"));
    assertEquals(EXPECTED_SERVICE_IN_ELSE, preprocessor.execute(ADAPTER_XML_WITH_IF_THEN, new ServiceTestConfig()));
  }

  @Test
  public void testServiceInIfElseCantFindService() throws Exception {
    // The parent service is missing in the list, won't find anything
    ServiceUniqueIdPreprocessor preprocessor = new ServiceUniqueIdPreprocessor("channel", "workflow", Arrays.asList("log-else"));
    PreprocessorException exception = assertThrows(PreprocessorException.class,
        () -> preprocessor.execute(ADAPTER_XML_WITH_IF_THEN, new ServiceTestConfig()));
    assertEquals(
        "Failed to find service: channel: [channel] workflow: [workflow] services: [[log-else]] xpath [//channel-list/*[unique-id = 'channel']/workflow-list/*[unique-id = 'workflow']/service-collection/services/*[unique-id = 'log-else']]",
        exception.getMessage());
  }

  @Test
  public void testExecuteNoMatch() throws Exception {
    try {
      ServiceUniqueIdPreprocessor preprocessor = new ServiceUniqueIdPreprocessor("channel-1", "workflow-1", Collections.singletonList("service-no-match"));
      preprocessor.execute(ADAPTER_XML, new ServiceTestConfig());
      fail();
    } catch (PreprocessorException e) {
      assertEquals("Failed to find service: channel: [channel-1] workflow: [workflow-1] services: [[service-no-match]] xpath [//channel-list/*[unique-id = 'channel-1']/workflow-list/*[unique-id = 'workflow-1']/service-collection/services/*[unique-id = 'service-no-match']]", e.getMessage());
    }
  }

  @Test
  public void testGenerateXpath() {
    ServiceUniqueIdPreprocessor preprocessor;
    preprocessor = new ServiceUniqueIdPreprocessor("channel-1", "workflow-1", Collections.singletonList("service-1"));
    assertEquals(CHANNEL_WORKFLOW_SINGLE_SERVICE_XPATH, preprocessor.generateXpath());
    preprocessor = new ServiceUniqueIdPreprocessor("channel-1", "workflow-1", Arrays.asList("service-1", "service-2"));
    assertEquals(CHANNEL_WORKFLOW_MULTI_SERVICE_XPATH, preprocessor.generateXpath());
    preprocessor = new ServiceUniqueIdPreprocessor("workflow-1", Collections.singletonList("service-1"));
    assertEquals(WORKFLOW_SINGLE_SERVICE_XPATH, preprocessor.generateXpath());
    preprocessor = new ServiceUniqueIdPreprocessor("workflow-1", Arrays.asList("service-1", "service-2"));
    assertEquals(WORKFLOW_MULTI_SERVICE_XPATH, preprocessor.generateXpath());
    preprocessor = new ServiceUniqueIdPreprocessor(Collections.singletonList("service-1"));
    assertEquals(SINGLE_SERVICE_XPATH, preprocessor.generateXpath());
    preprocessor = new ServiceUniqueIdPreprocessor(Arrays.asList("service-1", "service-2"));
    assertEquals(MULTI_SERVICE_XPATH, preprocessor.generateXpath());
  }

  @Override
  protected Preprocessor createPreprocessor() {
    return new ServiceUniqueIdPreprocessor("channel-1", "workflow-1", Arrays.asList("service-1", "service-2"));
  }

}
