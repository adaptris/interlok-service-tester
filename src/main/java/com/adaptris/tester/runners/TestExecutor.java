package com.adaptris.tester.runners;

import com.adaptris.core.AdaptrisMarshaller;
import com.adaptris.core.CoreException;
import com.adaptris.core.DefaultMarshaller;
import com.adaptris.core.config.PreProcessingXStreamMarshaller;
import com.adaptris.tester.report.junit.JUnitReportTestResults;
import com.adaptris.tester.runtime.ServiceTest;
import com.adaptris.tester.runtime.ServiceTestException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class TestExecutor {

  private String preProcessors;
  private String inputFilePath;
  private String outputFilePath;

  public static void main(String args[]) throws ServiceTestException {
    TestExecutor e = new TestExecutor();
    e.execute(args);
  }

  void execute(String[] args) throws ServiceTestException {
    checkAndSetArguments(args);
    execute(new File(getInputFilePath()), new File(getOutputFilePath()));
  }

  void checkAndSetArguments(String[] args) throws ServiceTestException{
    if (args.length < 2){
      throw new IllegalArgumentException("Missing arguments required [input-file] [output-file]");
    }
    setInputFilePath(args[0]);
    setOutputFilePath(args[1]);
    if (args.length > 2 ) {
      setPreProcessors(args[2]);
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
