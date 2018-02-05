package com.adaptris.tester.runtime.messages.metadata;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @service-test-config empty-metadata-provider
 */
@XStreamAlias("empty-metadata-provider")
public class EmptyMetadataProvider extends MetadataProvider {

  @XStreamOmitField
  private Map<String, String> messageHeaders;

  public EmptyMetadataProvider(){
    this.messageHeaders = new HashMap<>();
  }

  @Override
  public Map<String, String> getMessageHeaders() {
    return messageHeaders;
  }
}
