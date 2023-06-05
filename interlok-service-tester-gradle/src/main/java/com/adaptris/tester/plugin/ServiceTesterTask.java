package com.adaptris.tester.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import com.adaptris.core.util.Args;
import com.adaptris.tester.report.junit.JUnitReportTestResults;
import com.adaptris.tester.runners.TestExecutor;
import com.adaptris.tester.runtime.ServiceTestException;

public class ServiceTesterTask extends DefaultTask {

  @Input
  @Optional
  @InputFile
  private File serviceTest;

  @Input
  @Optional
  @OutputFile
  private File serviceTestOutput;

  public ServiceTesterTask() {
    setGroup("Verification");
    setDescription("Runs interlok-service-tester tests.");
    onlyIf(task -> getServiceTest().exists());
    setServiceTest(new File(getProject().getProjectDir(), "src/test/interlok/service-tester.xml"));
    setServiceTestOutput(new File(getProject().getBuildDir(), "test-results"));
  }

  @TaskAction
  public void serviceTester() throws ServiceTestException, IOException {
    TestExecutor executor = new TestExecutor();
    final byte[] encoded = Files.readAllBytes(getServiceTest().toPath());
    final String contents = new String(encoded, Charset.defaultCharset());
    final JUnitReportTestResults result = executor.execute(contents, getProject().getProjectDir());
    result.writeReports(getServiceTestOutput());
    if (result.hasFailures()) {
      throw new GradleException("Test failures");
    }
  }

  public File getServiceTest() {
    return serviceTest;
  }

  public void setServiceTest(File serviceTest) {
    this.serviceTest = Args.notNull(serviceTest, "serviceTest");
  }

  public File getServiceTestOutput() {
    return serviceTestOutput;
  }

  public void setServiceTestOutput(File serviceTestOutput) {
    this.serviceTestOutput = Args.notNull(serviceTestOutput, "serviceTestOutput");
  }

}
