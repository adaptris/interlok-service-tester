package com.adaptris.tester.runtime.services.preprocessor;

import com.adaptris.annotation.AutoPopulated;
import com.adaptris.util.KeyValuePair;
import com.adaptris.util.KeyValuePairSet;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config "find-and-replace-preprocessor
 */
@XStreamAlias("find-and-replace-preprocessor")
public class FindAndReplacePreprocessor implements Preprocessor {

  @AutoPopulated
  private KeyValuePairSet replacementKeys;

  public FindAndReplacePreprocessor(){
    replacementKeys = new KeyValuePairSet();
  }

  public void setReplacementKeys(KeyValuePairSet replacementKeys) {
    this.replacementKeys = replacementKeys;
  }

  public KeyValuePairSet getReplacementKeys() {
    return replacementKeys;
  }

  @Override
  public String execute(String input) throws PreprocessorException {

    for(KeyValuePair kvp :  replacementKeys.getKeyValuePairs()){
      input = input.replaceAll(kvp.getKey(), kvp.getValue());
    }
    return input;
  }
}
