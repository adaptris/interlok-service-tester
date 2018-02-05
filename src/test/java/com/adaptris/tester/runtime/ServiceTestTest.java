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

  public ServiceTestTest(String name) {
    super(name);
  }

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
}