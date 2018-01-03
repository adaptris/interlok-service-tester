package com.adaptris.tester.runtime;

/**
 * Standard Exception in the XpathCommon class.
 *
 * <p>Deliberately separate to allow different classes to rethrow there own exception.</p>
 */
public class XpathCommonException extends Exception {

  /**
   * Creates a new instance with a description of the Exception.
   * @param message description of the Exception
   */
  public XpathCommonException(String message) {
    super(message);
  }

  /**
   * Creates a new instance with a reference to a previous Exception and a description of the Exception.
   * @param message description of the Exception
   * @param e previous Exception
   */
  public XpathCommonException(String message, Exception e) {
    super(message, e);
  }
}
