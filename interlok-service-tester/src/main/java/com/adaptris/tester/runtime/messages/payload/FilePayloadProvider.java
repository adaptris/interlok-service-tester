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


import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.MessageException;
import com.adaptris.tester.utils.FsHelper;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Base64;

/**
 *
 * @service-test-config file-payload-provider
 */
@XStreamAlias("file-payload-provider")
public class FilePayloadProvider extends PayloadProvider {

  @XStreamOmitField
  private String payload;

  private String file;

  public FilePayloadProvider(){

  }

  public FilePayloadProvider(String file)  {
    setFile(file);
  }

  @Override
  public void init(ServiceTestConfig config) throws MessageException{
    try {
      final byte[] fileContents = FsHelper.getFileBytes(file, config);
      setPayload(Base64.getEncoder().encodeToString(fileContents));
    } catch (Exception e) {
      throw new MessageException("Failed to read file", e);
    }
  }

  public void setFile(String file){
    this.file = file;
  }

  public String getFile() {
    return file;
  }

  @Override
  public String getPayload(){
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }
}
