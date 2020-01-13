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

import com.adaptris.tester.STExampleConfigCase;
import com.adaptris.tester.runtime.messages.TestMessageProvider;
import com.adaptris.tester.runtime.messages.assertion.AssertMetadataContains;
import com.adaptris.tester.runtime.messages.metadata.EmptyMetadataProvider;
import com.adaptris.tester.runtime.messages.payload.InlinePayloadProvider;
import com.adaptris.tester.runtime.services.ServiceToTest;
import com.adaptris.tester.runtime.services.sources.InlineSource;

import java.util.Collections;

/**
 * @author mwarman
 */
public class ServiceTestTest extends STExampleConfigCase{

  @Override
  protected Object retrieveObjectForSampleConfig() {
    ServiceTest serviceTest = new ServiceTest();
    serviceTest.setUniqueId("AdapterTest");
    TestList testList = new TestList();
    serviceTest.addTestList(testList);
    testList.setUniqueId("TestList");
    Test test = new Test();
    testList.add(test);
    test.setUniqueId("Test1");
    ServiceToTest serviceToTest = new ServiceToTest();
    test.setServiceToTest(serviceToTest);
    InlineSource source = new InlineSource();
    source.setXml("\n" +
        "           <add-metadata-service>\n" +
        "            <unique-id>Add1</unique-id>\n" +
        "            <metadata-element>\n" +
        "             <key>key1</key>\n" +
        "             <value>val1</value>\n" +
        "            </metadata-element>\n" +
        "           </add-metadata-service>");
    serviceToTest.setSource(source);
    TestCase testCase = new TestCase();
    test.addTestCase(testCase);
    testCase.setUniqueId("TestCase1");
    testCase.setInputMessageProvider(new TestMessageProvider(new EmptyMetadataProvider(), new InlinePayloadProvider("<xml/>")));
    Assertions assertions = new Assertions();
    assertions.addAssertion(new AssertMetadataContains(Collections.singletonMap("key1", "val1")));
    testCase.setAssertions(assertions);
    return serviceTest;
  }

  @Override
  public boolean isAnnotatedForJunit4() {
    return true;
  }
}
