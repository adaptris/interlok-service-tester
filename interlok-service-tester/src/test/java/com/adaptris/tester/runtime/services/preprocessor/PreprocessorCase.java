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

package com.adaptris.tester.runtime.services.preprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.adaptris.tester.STExampleConfigCase;
import org.junit.jupiter.api.Test;

public abstract class PreprocessorCase extends STExampleConfigCase {

  /**
   * Key in unit-test.properties that defines where example goes unless overriden {@link #setBaseDir(String)}.
   *
   */
  public static final String BASE_DIR_KEY = "PreprocessorCase.baseDir";

  public PreprocessorCase() {
    if (PROPERTIES.getProperty(BASE_DIR_KEY) != null) {
      setBaseDir(PROPERTIES.getProperty(BASE_DIR_KEY));
    }
  }

  @Test
  public void testWrapException() {
    Exception e1 = new Exception();
    PreprocessorException wrapped = Preprocessor.wrapException(e1);
    assertEquals(e1, wrapped.getCause());
    PreprocessorException e2 = new PreprocessorException("XXX");
    wrapped = Preprocessor.wrapException(e2);
    assertEquals(e2, wrapped);
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return createPreprocessor();
  }

  protected abstract Preprocessor createPreprocessor();

}
