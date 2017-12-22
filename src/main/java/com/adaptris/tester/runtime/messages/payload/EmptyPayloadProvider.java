package com.adaptris.tester.runtime.messages.payload;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config empty-payload-provider
 */
@XStreamAlias("empty-payload-provider")
public class EmptyPayloadProvider extends PayloadProvider {


  @Override
  public String getPayload() {
    return "";
  }
}
