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
import org.junit.jupiter.api.Test;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.util.KeyValuePair;
import com.adaptris.util.KeyValuePairSet;

public class FindAndReplacePreprocessorTest extends PreprocessorCase {

  @Test
  public void testExecute() throws Exception {
    String result = createPreprocessor().execute("Hello foo", new ServiceTestConfig());
    assertEquals("Hello bar", result);

  }

  @Override
  protected FindAndReplacePreprocessor createPreprocessor(){
    FindAndReplacePreprocessor preprocessor = new FindAndReplacePreprocessor();
    KeyValuePairSet set = new KeyValuePairSet();
    set.addKeyValuePair(new KeyValuePair("foo", "bar"));
    preprocessor.setReplacementKeys(set);
    return preprocessor;
  }

}
