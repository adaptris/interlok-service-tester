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

package com.adaptris.tester.runtime.helpers.wiremock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.Test;

import com.adaptris.interlok.junit.scaffolding.ExampleConfigGenerator;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.helpers.DynamicPortProvider;
import com.adaptris.tester.runtime.helpers.Helper;

public class WireMockHelperTest extends ExampleConfigGenerator {

  public static final String BASE_DIR_KEY = "HelperCase.baseDir";

  public WireMockHelperTest() {
    if (PROPERTIES.getProperty(BASE_DIR_KEY) != null) {
      setBaseDir(PROPERTIES.getProperty(BASE_DIR_KEY));
    }
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return createHelper();
  }

  @Override
  protected String createExampleXml(Object object) throws Exception {
    String result = getExampleCommentHeader(object);
    result = result + configMarshaller.marshal(object);
    return result;
  }

  @Test
  public void testGet() throws Exception {
    final String serviceFile = "http_stubs";
    URL testFile = this.getClass().getClassLoader().getResource(serviceFile).toURI().toURL();

    WireMockHelper wireMockHelper = new WireMockHelper();
    DynamicPortProvider portProvider = new DynamicPortProvider(8080);
    wireMockHelper.setPortProvider(portProvider);
    wireMockHelper.setFileSource(testFile.toString());
    wireMockHelper.init(new ServiceTestConfig());

    URL url = new URL("http://localhost:" + portProvider.getPort() + "/hello");
    URLConnection urlConnection = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
    String inputLine;

    StringBuilder response = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    assertEquals("{\"hello\":\"world\"}", response.toString().replaceAll(" ", ""));
    assertTrue(wireMockHelper.getHelperProperties().containsKey("wire.mock.helper.port"));
    assertTrue(8080 <= Integer.parseInt(wireMockHelper.getHelperProperties().get("wire.mock.helper.port")));
    in.close();
    wireMockHelper.close();
  }

  @SuppressWarnings("resource")
  @Test
  public void testSyntaxError() throws Exception {
    WireMockHelper wireMockHelper = new WireMockHelper();
    DynamicPortProvider portProvider = new DynamicPortProvider(8080);
    wireMockHelper.setPortProvider(portProvider);
    wireMockHelper.setFileSource("invalid://uri^");

    ServiceTestException exception = assertThrows(ServiceTestException.class, () -> wireMockHelper.init(new ServiceTestConfig()));
    assertTrue(exception.getCause() instanceof URISyntaxException);
  }

  protected Helper createHelper() {
    WireMockHelper wireMockHelper = new WireMockHelper();
    DynamicPortProvider portProvider = new DynamicPortProvider(8080);
    wireMockHelper.setPortProvider(portProvider);
    wireMockHelper.setFileSource("/home/user/http_stubs");
    return wireMockHelper;
  }

}
