package com.adaptris.tester.runtime.messages.payload;

import com.adaptris.tester.runtime.messages.MessagesCase;
import org.junit.Test;

public class EmptyPayloadProviderTest extends MessagesCase {

  public EmptyPayloadProviderTest(String name) {
    super(name);
  }

  @Test
  public void getMessageHeaders() throws Exception {
    EmptyPayloadProvider m = new EmptyPayloadProvider();
    assertEquals("", m.getPayload());
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new EmptyPayloadProvider();
  }
}