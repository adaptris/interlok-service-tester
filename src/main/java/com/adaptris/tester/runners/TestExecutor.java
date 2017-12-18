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

  public static void main(String args[]) throws ServiceTestException {
    try {
      TestExecutor e = new TestExecutor();
      e.execute(args);
    } finally {
      LoggingConfigurator.newConfigurator().requestShutdown();
    }
  }

  void execute(String[] args) throws ServiceTestException {
    checkAndSetArguments(args);
    execute(new File(getInputFilePath()), new File(getOutputFilePath()));
  }

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

  private void execute(File input, File outputDirectory) throws ServiceTestException {
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

  private JUnitReportTestResults execute(String text) throws ServiceTestException {
    try {
      ServiceTest serviceTest = (ServiceTest) createMarshaller().unmarshal(text);
      return execute(serviceTest);
    } catch (CoreException e) {
      throw new ServiceTestException(e);
    }
  }

  private JUnitReportTestResults execute(ServiceTest serviceTest) throws ServiceTestException {
    return serviceTest.execute();
  }

  public void setPreProcessors(String preProcessors) {
    this.preProcessors = preProcessors;
  }

  public String getPreProcessors() {
    return preProcessors;
  }

  public void setInputFilePath(String inputFilePath) {
    this.inputFilePath = inputFilePath;
  }

  public String getInputFilePath() {
    return inputFilePath;
  }

  public void setOutputFilePath(String outputFilePath) {
    this.outputFilePath = outputFilePath;
  }

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
