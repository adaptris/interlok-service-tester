package com.adaptris.tester.runtime.services.preprocessor;

public interface Preprocessor {

  /**
   * Execute the preprocessors against the service input returning updated service.
   *
   * @param input Service to process
   * @return Processed service
   * @throws PreprocessorException wraps any thrown Exception
   */
  String execute(String input) throws PreprocessorException;
}
