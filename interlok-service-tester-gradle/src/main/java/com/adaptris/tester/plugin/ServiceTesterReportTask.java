package com.adaptris.tester.plugin;

import java.io.File;

import org.apache.tools.ant.taskdefs.XSLTProcess;
import org.apache.tools.ant.taskdefs.optional.junit.AggregateTransformer;
import org.apache.tools.ant.taskdefs.optional.junit.XMLResultAggregator;
import org.apache.tools.ant.types.FileSet;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

public class ServiceTesterReportTask extends DefaultTask {

  @InputDirectory
  private File serviceTestOutput;
  @OutputDirectory
  private File serviceTestReportOutput;
  @Input
  @Optional
  private File antStyleDir;

  public ServiceTesterReportTask() {
    setServiceTestOutput(new File(getProject().getBuildDir(), "test-results"));
    setServiceTestReportOutput(new File(getServiceTestOutput(), "html"));
  }

  @TaskAction
  public void report() {
    XMLResultAggregator aggregator = new XMLResultAggregator();
    aggregator.setProject(getAnt().getProject());
    aggregator.setTodir(getServiceTestOutput());
    FileSet fileSet = new FileSet();
    fileSet.setDir(getServiceTestOutput());
    fileSet.setIncludes("TEST-*.xml");
    aggregator.addFileSet(fileSet);
    AggregateTransformer transformer = aggregator.createReport();
    if (getAntStyleDir() != null) {
      transformer.setStyledir(getAntStyleDir());
    }
    XSLTProcess.Factory factory = transformer.createFactory();
    factory.setName("net.sf.saxon.TransformerFactoryImpl");
    transformer.setTodir(getServiceTestReportOutput());
    AggregateTransformer.Format format = new AggregateTransformer.Format();
    format.setValue(AggregateTransformer.FRAMES);
    transformer.setFormat(format);
    aggregator.execute();
  }

  public void setServiceTestOutput(File serviceTestOutput) {
    this.serviceTestOutput = serviceTestOutput;
  }

  public File getServiceTestOutput() {
    return serviceTestOutput;
  }

  public void setServiceTestReportOutput(File serviceTestReportOutput) {
    this.serviceTestReportOutput = serviceTestReportOutput;
  }

  public File getServiceTestReportOutput() {
    return serviceTestReportOutput;
  }

  public void setAntStyleDir(File antStyleDir) {
    this.antStyleDir = antStyleDir;
  }

  public File getAntStyleDir() {
    return antStyleDir;
  }

}
