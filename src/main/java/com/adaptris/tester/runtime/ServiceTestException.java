package com.adaptris.tester.runtime;

public class ServiceTestException extends Exception {

  private static final long serialVersionUID = -6781770817688644994L;

  public ServiceTestException(){
    super();
  }

  public ServiceTestException(String message, Exception e) {
    super(message, e);
  }

  public ServiceTestException(Exception e) {
    super(e);
  }
}
