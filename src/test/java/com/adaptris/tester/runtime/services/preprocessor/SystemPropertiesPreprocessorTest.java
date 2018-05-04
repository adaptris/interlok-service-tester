package com.adaptris.tester.runtime.services.preprocessor;

/**
 * @author mwarman
 */
public class SystemPropertiesPreprocessorTest extends PreprocessorCase {

  public SystemPropertiesPreprocessorTest(String name) {
    super(name);
  }

  public void testExecute() throws Exception{
    System.setProperty("foo", "bar");
    String result = createPreprocessor().execute("hello ${foo}");
    assertEquals("hello bar", result);
  }

  @Override
  protected Preprocessor createPreprocessor() {
    return new SystemPropertiesPreprocessor();
  }
}