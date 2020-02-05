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

package com.adaptris.tester.runtime.services.sources;

import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.utils.FsHelper;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @service-test-config file-source
 */
@XStreamAlias("file-source")
public class FileSource implements Source {


  private String file;

  public FileSource(){

  }

  public FileSource(String file){
    setFile(file);
  }

  @Override
  public String getSource(ServiceTestConfig config) throws SourceException {
    try {
      final byte[] fileContents = FsHelper.getFileBytes(file, config);
      return new String(fileContents);
    } catch (Exception e) {
      throw new SourceException("Failed to read file", e);
    }
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getFile() {
    return file;
  }
}
