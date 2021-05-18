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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.adaptris.core.util.Args;
import com.adaptris.tester.report.junit.JUnitReportTestResults;
import com.adaptris.tester.runtime.clients.TestClient;
import com.adaptris.tester.runtime.helpers.Helper;
import com.adaptris.tester.runtime.services.sources.DefaultConfigSource;
import com.adaptris.tester.runtime.services.sources.ParentSource;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Main class for service tester. Use other components from <code>tester</code> package to define tests execution.
 * The client class {@link com.adaptris.tester.runners.TestExecutor} create and manage instances of this class.
 *
 * @service-test-config service-test
 */
@XStreamAlias("service-test")
public class ServiceTest implements UniqueIdAwareTestComponent {

  private String uniqueId;

  private TestClient testClient;
  private ParentSource source;
  private List<Helper> helpers;
  @XStreamImplicit
  private List<TestList> testLists;

  private transient File workingDirectory = null;

  public ServiceTest(){
    setSource(new DefaultConfigSource());
    setHelpers(new ArrayList<Helper>());
    setTestLists(new ArrayList<TestList>());
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = Args.notBlank(uniqueId, "unique id");
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  public void setTestClient(TestClient testClient) {
    this.testClient = testClient;
  }

  public TestClient getTestClient() {
    return testClient;
  }

  public ParentSource getSource() {
    return source;
  }

  public void setSource(ParentSource source) {
    this.source = source;
  }

  public void setHelpers(List<Helper> helpers) {
    this.helpers = helpers;
  }

  public List<Helper> getHelpers() {
    return helpers;
  }

  public File getWorkingDirectory() {
    return workingDirectory;
  }

  public void setWorkingDirectory(File workingDirectory) {
    this.workingDirectory = workingDirectory;
  }

  void initHelpers(ServiceTestConfig config) throws ServiceTestException {
    for (Helper helper : getHelpers()) {
      helper.init(config);
    }
  }

  void closeHelpers()  {
    for (Helper helper : getHelpers()) {
      IOUtils.closeQuietly(helper, null);
    }
  }

  public Map<String, String> getHelperProperties(){
    Map<String,String> p = new HashMap<>();
    for (Helper helper : getHelpers()) {
      p.putAll(helper.getHelperProperties());
    }
    return p;
  }

  public void setTestLists(List<TestList> adapterTestLists) {
    testLists = adapterTestLists;
  }

  public List<TestList> getTestLists() {
    return testLists;
  }

  public void addTestList(TestList adapterTestList){
    testLists.add(adapterTestList);
  }

  public JUnitReportTestResults execute() throws ServiceTestException {
    ServiceTestConfig config = new ServiceTestConfig().withWorkingDirectory(getWorkingDirectory()).withSource(getSource());
    initHelpers(config);
    config.withHelperProperties(getHelperProperties());
    try (TestClient t = testClient.init(config)) {
      JUnitReportTestResults results = new JUnitReportTestResults(uniqueId);
      for (TestList tests : getTestLists()) {
        results.addTestSuites(tests.execute(t, config));
      }
      return results;
    } catch (IOException e) {
      throw new ServiceTestException(e);
    } finally {
      closeHelpers();
    }
  }
}
