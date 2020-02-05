package com.adaptris.tester.plugin;

import com.adaptris.core.stubs.TempFileUtils;
import com.adaptris.util.GuidGenerator;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.specs.Spec;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

  @SuppressWarnings("unchecked")
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
    Spec onlyIf =  task.getOnlyIf();
    assertTrue(onlyIf.isSatisfiedBy(task));
  }

  @SuppressWarnings("unchecked")
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
    Spec onlyIf =  task.getOnlyIf();
    assertFalse(onlyIf.isSatisfiedBy(task));
  }
}
