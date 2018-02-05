package com.adaptris.tester.runtime.messages.assertion;

/**
 * Class used to store assertion results.
 */
public class AssertionResult {

  private final String uniqueId;
  private final String type;
  private final boolean passed;
  private String message;

  public AssertionResult(String uniqueId, String type, boolean passed){
    this.uniqueId = uniqueId;
    this.type = type;
    this.passed = passed;
  }

  public AssertionResult(String uniqueId, String type, boolean passed, String message){
    this(uniqueId, type, passed);
    this.message = message;
  }

  /**
   * Returns whether assertion passed.
   * @return Did assertion pass.
   */
  public boolean isPassed() {
    return passed;
  }

  /**
   * Returns message, if message is null returns: "Assertion Failure [type]".
   * @return The assertion message.
   */
  public String getMessage() {
    if(message != null){
      return message;
    } else {
      return "Assertion Failure: [" + type + "]";
    }
  }
}
