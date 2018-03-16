package com.adaptris.tester.runtime.messages.metadata;

import java.util.Map;

import com.adaptris.tester.runtime.messages.MessageException;

public abstract class MetadataProvider {

  public void init() throws MessageException {

  }

  public abstract Map<String, String> getMessageHeaders();
}
