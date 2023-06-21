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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpTemplate;
import org.mockserver.model.JsonBody;

import com.adaptris.core.CoreException;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.helpers.Helper;
import com.adaptris.tester.runtime.helpers.PortProvider;
import com.adaptris.tester.runtime.helpers.StaticPortProvider;
import com.adaptris.tester.utils.FsHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Helper which starts a WireMock server.
 *
 * <p>
 * WireMock (<a href="http://wiremock.org/">http://wiremock.org/</a>):
 * </p>
 * <blockquote cite="http://wiremock.org/">
 * <p>
 * WireMock is a simulator for HTTP-based APIs. Some might consider it a service virtualization tool or a mock server.
 * </p>
 *
 * <p>
 * It enables you to stay productive when an API you depend on doesn't exist or isn't complete. It supports testing of edge cases and
 * failure modes that the real API won't reliably produce. And because it's fast it can reduce your build time from hours down to minutes.
 * </p>
 * </blockquote>
 *
 * @service-test-config wire-mock-helper
 */
@XStreamAlias("wire-mock-helper")
@Slf4j
public class WireMockHelper extends Helper {

  private static final String HTTP_REQUEST = "httpRequest";
  private static final String HTTP_RESPONSE = "httpResponse";
  private static final String PATH = "path";
  private static final String METHOD = "method";

  private static final String MAPPINGS_PATH = "mappings";

  /**
   * Key for helper property ({@value}).
   */
  public static final String WIRE_MOCK_HELPER_PORT_PROPERTY_NAME = "wire.mock.helper.port";

  private transient MockServerClient mockServer;

  /**
   * Sets the path of the mock configuration.
   *
   * @param fileSource
   *          Path of the mock configuration.
   */
  @Getter
  @Setter
  private String fileSource;

  /**
   * The {@link PortProvider} for the mock server
   */
  @Getter
  @Setter
  private PortProvider portProvider;

  public WireMockHelper() {
    setPortProvider(new StaticPortProvider());
  }

  /**
   * Initialises Mock server with port set as value from {@link #getPortProvider()} which also added as a helper property to
   * {@value #WIRE_MOCK_HELPER_PORT_PROPERTY_NAME}.
   *
   * @param config
   *          The service tester config
   * @throws ServiceTestException
   *           wrapping any thrown exception
   */
  @Override
  public void init(ServiceTestConfig config) throws ServiceTestException {
    getPortProvider().initPort();
    addHelperProperty(WIRE_MOCK_HELPER_PORT_PROPERTY_NAME, String.valueOf(getPortProvider().getPort()));
    File fileFromFileSource = getFileFromFileSource(config);
    mockServer = ClientAndServer.startClientAndServer(getPortProvider().getPort());

    mappings(fileFromFileSource).forEach(p -> addMapping(p));
  }

  private void addMapping(Path path) {
    try {
      JsonBody json = JsonBody.json(Files.readString(path));
      JsonNode httpRequest = json.get(HTTP_REQUEST);
      JsonNode httpResponse = json.get(HTTP_RESPONSE);
      mockServer.when(HttpRequest.request().withPath(httpRequest.get(PATH).textValue()).withMethod(httpRequest.get(METHOD).textValue()))
          .respond(HttpTemplate.template(HttpTemplate.TemplateType.MUSTACHE, httpResponse.toString()));
    } catch (IOException ioe) {
      log.error("Could not add server mock mapping for file {}", path, ioe);
    }
  }

  private Stream<Path> mappings(File sourceFile) throws ServiceTestException {
    try {
      return Files.list(new File(sourceFile, MAPPINGS_PATH).toPath()).filter(f -> Files.isRegularFile(f));
    } catch (IOException e) {
      throw new ServiceTestException(e);
    }
  }

  private File getFileFromFileSource(ServiceTestConfig config) throws ServiceTestException {
    try {
      return FsHelper.createFile(getFileSource(), config);
    } catch (IOException | URISyntaxException | CoreException e) {
      throw new ServiceTestException(e);
    }
  }

  /**
   * Closes WireMock server and release port from {@link #getPortProvider()}.
   *
   * @throws IOException
   */
  @Override
  public void close() throws IOException {
    mockServer.stop();
    getPortProvider().releasePort();
  }

}
