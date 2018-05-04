package com.adaptris.tester.runtime.services.preprocessor;


import com.adaptris.core.CoreException;
import com.adaptris.core.varsub.SystemPropertiesPreProcessor;
import com.adaptris.util.KeyValuePairSet;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config system-properties-substitution-preprocessor
 */
@XStreamAlias("system-properties-substitution-preprocessor")
public class SystemPropertiesPreprocessor implements Preprocessor {


  public SystemPropertiesPreprocessor(){}

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input) throws PreprocessorException {
    try {
      SystemPropertiesPreProcessor processor = new SystemPropertiesPreProcessor(new KeyValuePairSet());
      return processor.process(input);
    } catch (CoreException e) {
      throw new PreprocessorException("Failed to substitute variables", e);
    }
  }
}
