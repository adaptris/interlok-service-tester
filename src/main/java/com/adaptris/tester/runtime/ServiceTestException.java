package com.adaptris.tester.runtime;

public class ServiceTestException extends Exception {

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
