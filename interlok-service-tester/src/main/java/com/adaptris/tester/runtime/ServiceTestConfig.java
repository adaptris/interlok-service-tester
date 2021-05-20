package com.adaptris.tester.runtime;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.adaptris.tester.runtime.services.sources.ParentSource;

/**
 * @author mwarman
 */
public class ServiceTestConfig {
  public ParentSource source;
  public Map<String, String> helperProperties;
  public File workingDirectory;

  public static final String SERVICE_TESTER_WORKING_DIRECTORY = "service.tester.working.directory";

  public Properties workingDirectoryProperties;

  public ServiceTestConfig(){
    helperProperties = new HashMap<>();
    workingDirectoryProperties = new Properties();
    withWorkingDirectory(new File(System.getProperty("user.dir")));
  }

  /**
   * Add the ServiceTest source to the ServiceTestConfig
   *
   * @param source
   * @return this
   */
  public ServiceTestConfig withSource(ParentSource source) {
    this.source = source;
    return this;
  }

  public ServiceTestConfig withHelperProperties(Map<String, String> helperProperties){
    this.helperProperties = helperProperties;
    return this;
  }

  public ServiceTestConfig withWorkingDirectory(File workingDirectory) {
    try {
      if (workingDirectory != null) {
        this.workingDirectory = workingDirectory;
        workingDirectoryProperties.put(SERVICE_TESTER_WORKING_DIRECTORY,
            new URI(null, workingDirectory.getAbsolutePath(), null).toASCIIString());
      }
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    return this;
  }
}