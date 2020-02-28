package com.adaptris.tester.utils;

import static com.adaptris.core.varsub.Constants.DEFAULT_VARIABLE_POSTFIX;
import static com.adaptris.core.varsub.Constants.DEFAULT_VARIABLE_PREFIX;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import com.adaptris.core.CoreException;
import com.adaptris.core.varsub.SimpleStringSubstitution;
import com.adaptris.fs.FsException;
import com.adaptris.fs.FsWorker;
import com.adaptris.fs.NioWorker;
import com.adaptris.tester.runtime.ServiceTestConfig;

/**
 * @author mwarman
 */
public abstract class FsHelper {


  public static File createFile(String path, ServiceTestConfig config) throws IOException, URISyntaxException, CoreException {
    path = new SimpleStringSubstitution().doSubstitution(path, config.workingDirectoryProperties, DEFAULT_VARIABLE_PREFIX, DEFAULT_VARIABLE_POSTFIX);
    URL url = com.adaptris.core.fs.FsHelper.createUrlFromString(path, true);
    return com.adaptris.core.fs.FsHelper.createFileReference(url);
  }

  public static byte[] getFileBytes(String path, ServiceTestConfig config) throws IOException, URISyntaxException, FsException, CoreException {
    FsWorker fsWorker = new NioWorker();
    return fsWorker.get(createFile(path, config));
  }
}
