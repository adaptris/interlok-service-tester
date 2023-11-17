/*
    Copyright 2018 Adaptris Ltd.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.adaptris.tester.runners;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.adaptris.core.AdaptrisMarshaller;
import com.adaptris.core.config.PreProcessingXStreamMarshaller;
import com.adaptris.core.stubs.TempFileUtils;
import com.adaptris.util.GuidGenerator;

public class TestExecutorTest {

  @Test
  public void setPreProcessors() throws Exception {
    TestExecutor e = new TestExecutor();
    e.setPreProcessors("xinclude");

    assertEquals("xinclude", e.getPreProcessors());
  }

  @Test
  public void setInputFilePath() throws Exception {
    TestExecutor e = new TestExecutor();
    e.setInputFilePath("input");
    assertEquals("input", e.getInputFilePath());
  }

  @Test
  public void setOutputFilePath() throws Exception {
    TestExecutor e = new TestExecutor();
    e.setOutputFilePath("output");
    assertEquals("output", e.getOutputFilePath());
  }

  @Test
  public void createMarshaller() throws Exception {
    TestExecutor e = new TestExecutor();
    assertNotNull(e.createMarshaller());
    e.setPreProcessors("xinclude");
    AdaptrisMarshaller a = e.createMarshaller();
    assertNotNull(a);
    assertTrue(a instanceof PreProcessingXStreamMarshaller);
    assertEquals("xinclude", ((PreProcessingXStreamMarshaller) a).getPreProcessors());
  }

  @Test
  public void execute() throws Exception {
    final String serviceFile = "simple_sample.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    TestExecutor.main(new String[] { "-serviceTest", testFile.getAbsolutePath(), "-serviceTestOutput", tempDir.getAbsolutePath() });
    File expectedFile = new File(tempDir, "TEST-TestList.Test1.xml");
    assertTrue(expectedFile.exists());
  }

  @Test
  public void executeFailedTest() throws Exception {
    int exitCode = catchSystemExit(() -> {
      final String serviceFile = "simple_sample_fail.xml";
      File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
      GuidGenerator o = new GuidGenerator();
      File tempDir = TempFileUtils.createTrackedDir(o);
      TestExecutor.main(new String[] { "-serviceTest", testFile.getAbsolutePath(), "-serviceTestOutput", tempDir.getAbsolutePath() });
      File expectedFile = new File(tempDir, "TEST-TestList.Test1.xml");
      assertTrue(expectedFile.exists());
    });
    assertEquals(1, exitCode);
  }

  @Test
  public void testCheckAndSetArguments() throws Exception {
    TestExecutor e = new TestExecutor();
    try {
      e.checkAndSetArguments(new String[] {});
      fail();
    } catch (IllegalArgumentException ex) {
      assertEquals("Missing argument required [-serviceTest]", ex.getMessage());
    }
    e = new TestExecutor();
    e.checkAndSetArguments(new String[] { "-serviceTest", "input" });
    assertEquals("input", e.getInputFilePath());
    assertEquals("test-results", e.getOutputFilePath());
    assertNull(e.getPreProcessors());
    e = new TestExecutor();
    e.checkAndSetArguments(new String[] { "--serviceTest", "input" });
    assertEquals("input", e.getInputFilePath());
    assertEquals("test-results", e.getOutputFilePath());
    assertNull(e.getPreProcessors());
    e = new TestExecutor();
    e.checkAndSetArguments(new String[] { "-serviceTest", "input", "-serviceTestOutput", "output" });
    assertEquals("input", e.getInputFilePath());
    assertEquals("output", e.getOutputFilePath());
    assertNull(e.getPreProcessors());
    e = new TestExecutor();
    e.checkAndSetArguments(new String[] { "--serviceTest", "input", "--serviceTestOutput", "output" });
    assertEquals("input", e.getInputFilePath());
    assertEquals("output", e.getOutputFilePath());
    assertNull(e.getPreProcessors());
    e = new TestExecutor();
    e.checkAndSetArguments(
        new String[] { "-serviceTest", "input", "-serviceTestOutput", "output", "-serviceTestPreProcessors", "xinclude" });
    assertEquals("input", e.getInputFilePath());
    assertEquals("output", e.getOutputFilePath());
    assertEquals("xinclude", e.getPreProcessors());
    e = new TestExecutor();
    e.checkAndSetArguments(
        new String[] { "--serviceTest", "input", "--serviceTestOutput", "output", "--serviceTestPreProcessors", "xinclude" });
    assertEquals("input", e.getInputFilePath());
    assertEquals("output", e.getOutputFilePath());
    assertEquals("xinclude", e.getPreProcessors());
  }

}
