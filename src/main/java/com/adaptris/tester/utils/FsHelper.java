package com.adaptris.tester.utils;

import com.adaptris.fs.FsException;
import com.adaptris.fs.FsWorker;
import com.adaptris.fs.NioWorker;
import com.adaptris.tester.runtime.ServiceTestConfig;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author mwarman
 */
public class FsHelper {

  private FsHelper(){

  }

  public static byte[] getFileBytes(String path, ServiceTestConfig config) throws IOException, URISyntaxException, FsException {
    FsWorker fsWorker = new NioWorker();
    URL url = com.adaptris.core.fs.FsHelper.createUrlFromString(path, true);
    File fileToRead = com.adaptris.core.fs.FsHelper.createFileReference(url);
    if(!fileToRead.isAbsolute() && config.workingDirectory != null){
      fileToRead = Paths.get(config.workingDirectory.getPath(), fileToRead.getPath()).toFile();
    }
    return fsWorker.get(fileToRead);
  }
}
