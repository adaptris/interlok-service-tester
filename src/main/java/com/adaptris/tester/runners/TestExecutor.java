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

package com.adaptris.tester.runners;

import com.adaptris.core.AdaptrisMarshaller;
import com.adaptris.core.CoreException;
import com.adaptris.core.DefaultMarshaller;
import com.adaptris.core.config.PreProcessingXStreamMarshaller;
import com.adaptris.core.management.ArgUtil;
import com.adaptris.core.management.logging.LoggingConfigurator;
import com.adaptris.tester.report.junit.JUnitReportTestResults;
import com.adaptris.tester.runtime.ServiceTest;
import com.adaptris.tester.runtime.ServiceTestException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * <p>Entry point into service tester via the commandline.</p>
 *
 * <table>
 *   <tr><th>Argument</th><th>Description</th><th>Required</th></tr>
 *   <tr><td><code>-serviceTest</code></td><td>Test configuration location</td><td>true</td></tr>
 *   <tr><td><code>-serviceTestOutput</code></td><td>Output directory for test results (default: ./test-results)</td><td>false</td></tr>
 *   <tr><td><code>-serviceTestPreProcessor</code></td><td>Pre-processors to execute against test configuration (ex: xinclude)</td><td>false</td></tr>
 * </table>
 */
public class TestExecutor {

  private static final String[] ARG_INPUT = new String[]{"-serviceTest", "--serviceTest"};
  private static final String[] ARG_OUPUT = new String[]{"-serviceTestOutput", "--serviceTestOutput"};
  private static final String[] ARG_PREPROCESSORS = new String[]{"-serviceTestPreProcessors", "--serviceTestPreProcessors"};

  private String preProcessors;
  private String inputFilePath;
  private String outputFilePath;

  public TestExecutor(){
    setOutputFilePath("test-results");
  }

  /**
   * Entry point into service tester via the commandline, executes {@link #execute(String[])}.
   * @param args service tester arguments
   * @throws ServiceTestException wrapping any exception
   */
  public static void main(String args[]) throws ServiceTestException {
    try {
      TestExecutor e = new TestExecutor();
      e.execute(args);
    } finally {
      LoggingConfigurator.newConfigurator().requestShutdown();
    }
  }

  /**
   * Passes arguments using {@link #checkAndSetArguments(String[])} and executes {@link #execute(File, File)}.
   *
   * @param args service tester arguments
   * @throws ServiceTestException wrapping any exception
   */
  public void execute(String[] args) throws ServiceTestException {
    checkAndSetArguments(args);
    execute(new File(getInputFilePath()), new File(getOutputFilePath()));
  }

  /**
   * Check required arguments and sets then using there corresponding setters.
   *
   * @param args service tester arguments
   * @throws ServiceTestException wrapping any exception
   */
  void checkAndSetArguments(String[] args) throws ServiceTestException {
    ArgUtil argUtil;
    try {
      argUtil = ArgUtil.getInstance(args);
    } catch (Exception e) {
      throw new ServiceTestException("Failed to parse arguments", e);
    }
    if (argUtil.hasArgument(ARG_INPUT)) {
      setInputFilePath(argUtil.getArgument(ARG_INPUT));
    } else {
      throw new IllegalArgumentException("Missing argument required [-serviceTest]");
    }
    if (argUtil.hasArgument(ARG_OUPUT)) {
      setOutputFilePath(argUtil.getArgument(ARG_OUPUT));
    }
    if (argUtil.hasArgument(ARG_PREPROCESSORS)) {
      setPreProcessors(argUtil.getArgument(ARG_PREPROCESSORS));
    }

  }

  /**
   * Reads contents of input and executes {@link #execute(String)} using output directory with the returned results
   * {@link JUnitReportTestResults#writeReports(File)}
   *
   * @param input {@linkplain File} location of service test configuration.
   * @param outputDirectory {@linkplain File} location of output directory.
   * @throws ServiceTestException wrapping any exception
   */
  public void execute(File input, File outputDirectory) throws ServiceTestException {
    try {
      final byte[] encoded = Files.readAllBytes(input.toPath());
      final String contents = new String(encoded, Charset.defaultCharset());
      final JUnitReportTestResults result = execute(contents);
      result.writeReports(outputDirectory);
      if(result.hasFailures()){
        System.exit(1);
      }
    } catch (IOException e) {
      throw new ServiceTestException(e);
    }
  }

  /**
   * Unmarshalls input to {@link ServiceTest} using {@link AdaptrisMarshaller} executes {@link #execute(ServiceTest)}
   * returning {@link JUnitReportTestResults}
   *
   * @param text Test configuration
   * @return Test results in {@link JUnitReportTestResults}
   * @throws ServiceTestException wrapping any exception
   */
  public JUnitReportTestResults execute(String text) throws ServiceTestException {
    try {
      ServiceTest serviceTest = (ServiceTest) createMarshaller().unmarshal(text);
      return execute(serviceTest);
    } catch (CoreException e) {
      throw new ServiceTestException(e);
    }
  }

  /**
   * Executes {@link ServiceTest} configuration returning {@link JUnitReportTestResults}
   *
   * @param serviceTest Test configuration
   * @return Test results in {@link JUnitReportTestResults}
   * @throws ServiceTestException wrapping any exception
   */
  public JUnitReportTestResults execute(ServiceTest serviceTest) throws ServiceTestException {
    return serviceTest.execute();
  }

  /**
   * Sets Pre-Processors to be when service test configuration is unmarshalled.
   * @param preProcessors Pre-processors for service test configuration
   */
  public void setPreProcessors(String preProcessors) {
    this.preProcessors = preProcessors;
  }

  /**
   * Gets Pre-Processors to be when service test configuration is unmarshalled.
   * @return Pre-processors for service test configuration
   */
  public String getPreProcessors() {
    return preProcessors;
  }

  /**
   * Sets test configuration path
   * @param inputFilePath Test configuration path
   */
  public void setInputFilePath(String inputFilePath) {
    this.inputFilePath = inputFilePath;
  }

  /**
   * Gets test configuration path
   * @return Test configuration path
   */
  public String getInputFilePath() {
    return inputFilePath;
  }

  /**
   * Sets output directory for test results
   * @param outputFilePath Output directory for test results
   */
  public void setOutputFilePath(String outputFilePath) {
    this.outputFilePath = outputFilePath;
  }

  /**
   * Gets output directory for test results
   * @return Output directory for test results (default: test-results)
   */
  public String getOutputFilePath() {
    return outputFilePath;
  }

  AdaptrisMarshaller createMarshaller() throws CoreException{
    if (getPreProcessors() != null) {
      PreProcessingXStreamMarshaller marshaller = new PreProcessingXStreamMarshaller();
      marshaller.setPreProcessors(getPreProcessors());
      return marshaller;
    } else {
      return DefaultMarshaller.getDefaultMarshaller();
    }
  }
}
