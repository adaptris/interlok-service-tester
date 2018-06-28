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

import com.adaptris.core.fs.FsHelper;
import com.adaptris.fs.FsWorker;
import com.adaptris.fs.NioWorker;
import com.adaptris.tester.runtime.messages.MessageException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.File;
import java.net.URL;

/**
 *
 * @service-test-config file-payload-provider
 */
@XStreamAlias("file-payload-provider")
public class FilePayloadProvider extends PayloadProvider {

  @XStreamOmitField
  private String payload;

  @XStreamOmitField
  private transient FsWorker fsWorker = new NioWorker();

  private String file;

  public FilePayloadProvider(){

  }

  public FilePayloadProvider(String file)  {
    setFile(file);
  }

  public void init() throws MessageException{
    try {
      URL url = FsHelper.createUrlFromString(file, true);
      File fileToRead = FsHelper.createFileReference(url);
      final byte[] fileContents = fsWorker.get(fileToRead);
      setPayload(new String(fileContents));
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
    return this.payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }
}
