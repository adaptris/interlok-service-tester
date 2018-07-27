package com.adaptris.tester.plugin;

import com.adaptris.core.util.Args;
import com.adaptris.tester.runners.TestExecutor;
import com.adaptris.tester.runtime.ServiceTestException;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.*;

import java.io.File;

public class ServiceTesterTask extends DefaultTask {

  @Input
  @Optional
  @InputFile
  private File serviceTest;

  @Input
  @Optional
  @OutputFile
  private File serviceTestOutput;

  public ServiceTesterTask(){
    setGroup("Verification");
    setDescription("Runs interlok-service-tester tests.");
    onlyIf(task -> getServiceTest().exists());
    setServiceTest(new File(getProject().getProjectDir(), "src/test/interlok/service-tester.xml"));
    setServiceTestOutput(new File(getProject().getBuildDir(), "test-results"));
  }


  @TaskAction
  public void serviceTester() throws ServiceTestException {
    TestExecutor executor = new TestExecutor();
    executor.execute(getServiceTest(), getServiceTestOutput());
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
