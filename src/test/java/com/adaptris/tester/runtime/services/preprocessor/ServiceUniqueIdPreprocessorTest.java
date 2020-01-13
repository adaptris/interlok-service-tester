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

import com.adaptris.tester.runtime.ServiceTestConfig;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
  private static final String CHANNEL_WORKFLOW_MULTI_SERVICE_XPATH = CHANNEL_WORKFLOW_SINGLE_SERVICE_XPATH + "/services/*[unique-id = 'service-2']";

  private static final String WORKFLOW_SINGLE_SERVICE_XPATH = "//workflow-list/*[unique-id = 'workflow-1']/service-collection/services/*[unique-id = 'service-1']";
  private static final String WORKFLOW_MULTI_SERVICE_XPATH = WORKFLOW_SINGLE_SERVICE_XPATH + "/services/*[unique-id = 'service-2']";

  private static final String SINGLE_SERVICE_XPATH = "//*[unique-id = 'service-1']";
  private static final String MULTI_SERVICE_XPATH = SINGLE_SERVICE_XPATH + "/services/*[unique-id = 'service-2']";

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

  @Override
  public boolean isAnnotatedForJunit4() {
    return true;
  }
}
