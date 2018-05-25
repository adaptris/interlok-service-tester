/*
    Copyright 2018 Adaptris Ltd.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.adaptris.tester.runtime.messages;

import com.adaptris.core.SerializableAdaptrisMessage;
import com.adaptris.interlok.types.SerializableMessage;

public class MessageTranslator {

  public TestMessage translate(SerializableMessage message){
    TestMessage tm = new TestMessage(message.getMessageHeaders(), message.getContent());
    tm.setNextServiceId(message.getNextServiceId());
    return tm;
  }

  public SerializableAdaptrisMessage translate(TestMessage input) {
    SerializableAdaptrisMessage message = new SerializableAdaptrisMessage();
    message.setContent(input.getPayload());
    message.setMessageHeaders(input.getMessageHeaders());
    message.setNextServiceId(null);
    return message;
  }
}
