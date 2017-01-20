package com.adaptris.tester.runtime.messages.metadata;

import com.adaptris.tester.runtime.messages.MessagesCase;
import org.junit.Test;

public class EmptyMetadataProviderTest extends MessagesCase {

  public EmptyMetadataProviderTest(String name) {
    super(name);
  }

  @Test
  public void getMessageHeaders() throws Exception {
    EmptyMetadataProvider m = new EmptyMetadataProvider();
    assertEquals(0, m.getMessageHeaders().size());
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new EmptyMetadataProvider();
  }
}