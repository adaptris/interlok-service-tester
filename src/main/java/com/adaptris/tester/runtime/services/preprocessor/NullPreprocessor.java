package com.adaptris.tester.runtime.services.preprocessor;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config null-preprocessor
 */
@XStreamAlias("null-preprocessor")
public class NullPreprocessor implements Preprocessor{

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input) throws PreprocessorException {
    return input;
  }
}
