package com.adaptris.tester.plugin;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceTesterPluginTest {

  @Test
  public void serviceTesterPluginAddsTasksToProject(){
    Project project = ProjectBuilder.builder().build();
    project.getPluginManager().apply("com.adaptris.interlok-service-tester");
    Task tester = project.getTasks().getByName("interlokServiceTester");
    assertTrue(tester instanceof ServiceTesterTask);
    Task reporter = project.getTasks().getByName("interlokServiceTesterReport");
    assertTrue(reporter instanceof ServiceTesterReportTask);
  }
}
