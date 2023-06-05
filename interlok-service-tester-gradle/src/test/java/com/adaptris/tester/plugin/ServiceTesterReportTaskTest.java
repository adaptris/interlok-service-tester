package com.adaptris.tester.plugin;

import com.adaptris.core.stubs.TempFileUtils;
import com.adaptris.util.GuidGenerator;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTesterReportTaskTest {

  @Test
  public void report() throws Exception {
    final String serviceFile = "simple_sample.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    Project project = ProjectBuilder.builder().build();
    ServiceTesterTask tester = project.getTasks().create("interlokServiceTester", ServiceTesterTask.class);
    tester.setServiceTest(testFile);
    tester.setServiceTestOutput(tempDir);
    tester.serviceTester();
    ServiceTesterReportTask task = project.getTasks().create("interlokServiceTesterReporter",ServiceTesterReportTask.class);
    task.setServiceTestOutput(tempDir);
    task.setServiceTestReportOutput(new File(tempDir, "html"));
    task.report();
    File expectedFile = new File(tempDir, "html");
    assertTrue(expectedFile.exists());
  }
}