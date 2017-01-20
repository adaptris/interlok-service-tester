package com.adaptris.tester.runtime;

import com.adaptris.tester.STExampleConfigCase;
import com.adaptris.tester.runtime.services.sources.Source;
import com.adaptris.util.GuidGenerator;

public abstract class TCCase extends STExampleConfigCase {

  /**
   * Key in unit-test.properties that defines where example goes unless overriden {@link #setBaseDir(String)}.
   *
   */
  public static final String BASE_DIR_KEY = "TestCase.baseDir";

  public TCCase(String name) {
    super(name);
    if (PROPERTIES.getProperty(BASE_DIR_KEY) != null) {
      setBaseDir(PROPERTIES.getProperty(BASE_DIR_KEY));
    }
  }

  protected TestCase createBaseTestCase(){
    TestCase testCase = new TestCase();
    GuidGenerator guidGenerator = new GuidGenerator();
    testCase.setUniqueId(guidGenerator.getUUID());
    return testCase;
  }

}
