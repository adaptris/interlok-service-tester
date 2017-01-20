package com.adaptris.tester.runners;

import com.adaptris.core.AdaptrisMarshaller;
import com.adaptris.core.config.PreProcessingXStreamMarshaller;
import com.adaptris.core.stubs.TempFileUtils;
import com.adaptris.util.GuidGenerator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.File;

import static org.junit.Assert.*;

public class TestExecutorTest  {

  @Rule
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

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
    assertEquals("xinclude",((PreProcessingXStreamMarshaller)a).getPreProcessors());
  }

  @Test
  public void execute() throws Exception{
    final String serviceFile = "simple_sample.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    TestExecutor.main(new String[]{testFile.getAbsolutePath(), tempDir.getAbsolutePath()});
    File expectedFile = new File(tempDir, "TEST-TestList.Test1.xml");
    assertTrue(expectedFile.exists());
  }

  @Test
  public void executeFailedTest() throws Exception{
    exit.expectSystemExitWithStatus(1);
    final String serviceFile = "simple_sample_fail.xml";
    File testFile = new File(this.getClass().getClassLoader().getResource(serviceFile).getFile());
    GuidGenerator o = new GuidGenerator();
    File tempDir = TempFileUtils.createTrackedDir(o);
    TestExecutor.main(new String[]{testFile.getAbsolutePath(), tempDir.getAbsolutePath()});
    File expectedFile = new File(tempDir, "TEST-TestList.Test1.xml");
    assertTrue(expectedFile.exists());
  }


  @Test
  public void testCheckAndSetArguments() throws Exception {
    TestExecutor e = new TestExecutor();
    try {
      e.checkAndSetArguments(new String[]{});
      fail();
    } catch (IllegalArgumentException ex){
      assertEquals("Missing arguments required [input-file] [output-file]", ex.getMessage());
    }
    e = new TestExecutor();
    try {
      e.checkAndSetArguments(new String[]{"input"});
      fail();
    } catch (IllegalArgumentException ex){
      assertEquals("Missing arguments required [input-file] [output-file]", ex.getMessage());
    }
    e = new TestExecutor();
    e.checkAndSetArguments(new String[]{"input", "output"});
    assertEquals("input", e.getInputFilePath());
    assertEquals("output", e.getOutputFilePath());
    assertNull(e.getPreProcessors());
    e = new TestExecutor();
    e.checkAndSetArguments(new String[]{"input", "output", "xinclude"});
    assertEquals("input", e.getInputFilePath());
    assertEquals("output", e.getOutputFilePath());
    assertEquals("xinclude", e.getPreProcessors());
  }

}