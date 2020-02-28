package com.adaptris.tester.runtime;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author mwarman
 */
public class ServiceTestConfig {
  public Map<String, String> helperProperties;
  public File workingDirectory;

  public static final String SERVICE_TESTER_WORKING_DIRECTORY = "service.tester.working.directory";

  public Properties workingDirectoryProperties;

  public ServiceTestConfig(){
    this.helperProperties = new HashMap<>();
    this.workingDirectoryProperties = new Properties();
    withWorkingDirectory(new File(System.getProperty("user.dir")));
  }

  public ServiceTestConfig withHelperProperties(Map<String, String> helperProperties){
    this.helperProperties = helperProperties;
    return this;
  }

  public ServiceTestConfig withWorkingDirectory(File workingDirectory) {
    try {
      if (workingDirectory != null) {
        this.workingDirectory = workingDirectory;
        this.workingDirectoryProperties.put(SERVICE_TESTER_WORKING_DIRECTORY,
            new URI(null, workingDirectory.getAbsolutePath(), null).toASCIIString());
      }
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    return this;
  }
}