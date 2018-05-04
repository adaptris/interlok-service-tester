package com.adaptris.tester.runtime.services.preprocessor;

import com.adaptris.tester.runtime.XpathCommon;
import com.adaptris.tester.runtime.XpathCommonException;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config xpath-preprocessor
 */
@XStreamAlias("xpath-preprocessor")
public class XpathPreprocessor extends XpathCommon implements Preprocessor {

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input) throws PreprocessorException {
    try {
      return nodeToString(selectSingleNode(input));
    } catch (XpathCommonException e) {
      throw new PreprocessorException(e.getMessage(), e);
    }
  }
}
