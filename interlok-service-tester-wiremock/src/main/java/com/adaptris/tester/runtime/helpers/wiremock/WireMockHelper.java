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

import com.adaptris.core.CoreException;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.helpers.Helper;
import com.adaptris.tester.runtime.helpers.PortProvider;
import com.adaptris.tester.runtime.helpers.StaticPortProvider;
import com.adaptris.tester.utils.FsHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.thoughtworks.xstream.annotations.XStreamAlias;

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
public class WireMockHelper extends Helper {

  /**
   * Key for helper property ({@value}).
   */
  public static final String WIRE_MOCK_HELPER_PORT_PROPERTY_NAME = "wire.mock.helper.port";

  private transient WireMockServer wireMockServer;

  private String fileSource;

  private PortProvider portProvider;

  public WireMockHelper() {
    setPortProvider(new StaticPortProvider());
  }

  /**
   * Initialises WireMock server with port set as value from {@link #getPortProvider()} which also added as a helper property to
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
    wireMockServer = new WireMockServer(WireMockConfiguration.options().port(getPortProvider().getPort())
        .fileSource(new SingleRootFileSource(getFileFromFileSource(config))).enableBrowserProxying(false).jettyStopTimeout(10000L));
    wireMockServer.start();
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
    wireMockServer.stop();
    getPortProvider().releasePort();
  }

  /**
   * Returns the path of WireMock configuration.
   *
   * @return Path of WireMock configuration.
   */
  public String getFileSource() {
    return fileSource;
  }

  /**
   * Sets the path of WireMock configuration.
   *
   * @param fileSource
   *          Path of WireMock configuration.
   */
  public void setFileSource(String fileSource) {
    this.fileSource = fileSource;
  }

  /**
   * Returns the {@link PortProvider}
   *
   * @return The port provider
   */
  public PortProvider getPortProvider() {
    return portProvider;
  }

  /**
   * Sets the {@link PortProvider}
   *
   * @param portProvider
   *          The port provider
   */
  public void setPortProvider(PortProvider portProvider) {
    this.portProvider = portProvider;
  }

}
