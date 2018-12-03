package com.adaptris.tester.runtime;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mwarman
 */
public class ServiceTestConfig {
  public Map<String, String> helperProperties;
  public File workingDirectory;

  public ServiceTestConfig(){
    this.helperProperties = new HashMap<>();
  }

  public ServiceTestConfig withHelperProperties(Map<String, String> helperProperties){
    this.helperProperties = helperProperties;
    return this;
  }

  public ServiceTestConfig withWorkingDirectory(File workingDirectory){
    this.workingDirectory = workingDirectory;
    return this;
  }
}