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

package com.adaptris.tester.runtime.messages.payload;

import com.adaptris.core.MultiPayloadAdaptrisMessageImp;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;

import java.util.HashMap;
/**
 *
 * @service-test-config multi-payload
 */
@XStreamAlias("multi-payload")
@XStreamConverter(MapConverter.class)
public class MultiPayload extends HashMap<String, MultiPayloadAdaptrisMessageImp.Payload> {

}
