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

import com.adaptris.annotation.DisplayOrder;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.XpathCommon;
import com.adaptris.tester.runtime.XpathCommonException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Returns service behind the scenes it generates an xpath.
 *
 * <pre>
 *   {@code
 *   //channel-list/*[unique-id = 'channel-1']/workflow-list/*[unique-id = 'workflow-1']/service-collection/services/*[unique-id = 'service-1']
 *   }
 * </pre>
 *
 * Multiple services assumes that the previous id was a service list {@code //*[unique-id = 'service-list-1']/services/*[unique-id = 'service-1']}.
 *
 * @service-test-config service-preprocessor
 */
@XStreamAlias("service-unique-id-preprocessor")
@DisplayOrder(order = {"channel", "workflow", "services"})
public class ServiceUniqueIdPreprocessor implements Preprocessor {

  private static final String CHANNEL_FORMAT = "/channel-list/*[unique-id = '%s']";
  private static final String WORKFLOW_FORMAT = "/workflow-list/*[unique-id = '%s']/service-collection/services";
  private static final String SERVICES_FORMAT = "/*[unique-id = '%s']";
  private static final String SERVICES = "/services";

  private String channel;
  private String workflow;
  @XStreamImplicit(itemFieldName = "service")
  private List<String> services;

  public ServiceUniqueIdPreprocessor(){
    this(new ArrayList<String>());
  }

  public ServiceUniqueIdPreprocessor(List<String> services){
    setServices(services);
  }

  public ServiceUniqueIdPreprocessor(String workflow, List<String> services){
    this(services);
    setWorkflow(workflow);
  }

  public ServiceUniqueIdPreprocessor(String channel, String workflow, List<String> services){
    this(workflow, services);
    setChannel(channel);
  }

  @Override
  public String execute(String input, ServiceTestConfig config) throws PreprocessorException {
    String xpath = generateXpath();
    XpathCommon xpathCommon = new XpathCommon();
    xpathCommon.setXpath(xpath);
    try {
      return xpathCommon.nodeToString(xpathCommon.selectSingleNode(input));
    } catch (XpathCommonException e) {
      throw new PreprocessorException(String.format("Failed to find service: channel: [%s] workflow: [%s] services: [%s] xpath [%s]", getChannel(), getWorkflow(), getServices(), xpath), e);
    }
  }

  String generateXpath(){
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("/");
    boolean extraSlash = false;
    if(getChannel() != null) {
      stringBuilder.append(String.format(CHANNEL_FORMAT, getChannel()));
      extraSlash = true;
    }
    if(getWorkflow() != null) {
      stringBuilder.append(String.format(WORKFLOW_FORMAT, getWorkflow()));
      extraSlash = true;
    }
    Iterator<String> services = getServices().iterator();
    while (services.hasNext()){
      String service = services.next();
      if (extraSlash) {
        stringBuilder.append("/");
      }
      stringBuilder.append(String.format(SERVICES_FORMAT, service));
      if(services.hasNext()){
        stringBuilder.append(SERVICES);
      }
    }
    return stringBuilder.toString();
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getWorkflow() {
    return workflow;
  }

  public void setWorkflow(String workflow) {
    this.workflow = workflow;
  }

  public List<String> getServices() {
    return services;
  }

  public void setServices(List<String> services) {
    this.services = services;
  }
}
