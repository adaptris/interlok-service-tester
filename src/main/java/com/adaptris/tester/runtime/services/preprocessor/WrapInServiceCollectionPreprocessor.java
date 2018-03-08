package com.adaptris.tester.runtime.services.preprocessor;

import com.adaptris.util.GuidGenerator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config wrap-in-sc-preprocessor
 */
@XStreamAlias("wrap-in-sc-preprocessor")
public class WrapInServiceCollectionPreprocessor implements Preprocessor{

  /**
   * {@inheritDoc}
   */
  @Override
  public String execute(String input) throws PreprocessorException {
    GuidGenerator guidGenerator = new GuidGenerator();
    String guid = guidGenerator.getUUID();
    String start = "<service-collection class=\"service-list\"><unique-id>sc-wrap-" + guid +"</unique-id><services>";
    String finish = "</services></service-collection>";
    return start + input + finish;
  }
}
