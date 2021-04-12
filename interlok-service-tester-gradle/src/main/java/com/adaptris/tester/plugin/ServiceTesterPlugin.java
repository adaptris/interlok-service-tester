package com.adaptris.tester.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class ServiceTesterPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    Task tester = project.getTasks().create("interlokServiceTester", ServiceTesterTask.class);
    Task reporter = project.getTasks().create("interlokServiceTesterReport", ServiceTesterReportTask.class);
    tester.finalizedBy(reporter);
    reporter.onlyIf(task -> tester.getDidWork());
  }
}
