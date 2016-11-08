package com.adaptris.tester.runtime.messages.payload;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("empty-payload-provider")
public class EmptyPayloadProvider extends PayloadProvider {


  @Override
  public String getPayload() {
    return "";
  }
}
