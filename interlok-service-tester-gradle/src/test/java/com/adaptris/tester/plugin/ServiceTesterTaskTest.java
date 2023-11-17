package com.adaptris.tester.plugin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.internal.TaskInternal;
import org.gradle.api.specs.Spec;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import com.adaptris.core.stubs.TempFileUtils;
import com.adaptris.util.GuidGenerator;

public class ServiceTesterTaskTest {

  @Test
  public void serviceTester() throws Exception{
    final String serviceFile = "simple_sample.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    Project project = ProjectBuilder.builder().build();
    ServiceTesterTask task = project.getTasks().create("interlokServiceTester", ServiceTesterTask.class);
    task.setServiceTest(testFile);
    task.setServiceTestOutput(tempDir);
    task.serviceTester();
    File expectedFile = new File(tempDir, "TEST-TestList.Test1.xml");
    assertTrue(expectedFile.exists());
  }

  @Test
  public void serviceTesterFailed() throws Exception{
    final String serviceFile = "simple_sample_fail.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    Project project = ProjectBuilder.builder().build();
    ServiceTesterTask task = project.getTasks().create("interlokServiceTester", ServiceTesterTask.class);
    task.setServiceTest(testFile);
    task.setServiceTestOutput(tempDir);
    try {
      task.serviceTester();
      fail();
    } catch (GradleException expected){

    }
    File expectedFile = new File(tempDir, "TEST-TestList.Test1.xml");
    assertTrue(expectedFile.exists());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void onlyIf() throws Exception {
    final String serviceFile = "simple_sample.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    Project project = ProjectBuilder.builder().build();
    ServiceTesterTask task = project.getTasks().create("interlokServiceTester", ServiceTesterTask.class);
    task.setServiceTest(testFile);
    task.setServiceTestOutput(tempDir);
    Spec<? super TaskInternal> onlyIf = task.getOnlyIf();
    assertTrue(onlyIf.isSatisfiedBy(task));
  }

  @SuppressWarnings("deprecation")
  @Test
  public void onlyIfMissing() throws Exception {
    final String serviceFile = "simple_sample1.xml";
    File testFile = new File(new File(this.getClass().getClassLoader().getResource(".").getFile()), serviceFile);
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    Project project = ProjectBuilder.builder().build();
    ServiceTesterTask task = project.getTasks().create("interlokServiceTester", ServiceTesterTask.class);
    task.setServiceTest(testFile);
    task.setServiceTestOutput(tempDir);
    Spec<? super TaskInternal> onlyIf = task.getOnlyIf();
    assertFalse(onlyIf.isSatisfiedBy(task));
  }
}
