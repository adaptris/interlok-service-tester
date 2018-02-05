package com.adaptris.tester.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Set;

/**
 * Utility class to perform variable substitution.
 */
public class SimpleStringSubstitution {

  private transient Logger log = LoggerFactory.getLogger(this.getClass().getName());

  /**
   * Replaces input string with variables.
   *
   * @param input String to perform substitution
   * @param variableSubs Variables to substitute
   * @param variablePrefix Prefix of variables in input
   * @param variablePostFix PostFix of variables in input
   * @return Input with variables replaced
   */
  public String doSubstitution(String input, Properties variableSubs, String variablePrefix, String variablePostFix) {
    Set<String> keySet = variableSubs.stringPropertyNames();
    log.trace("Performing configuration variable substitution");
    for (String key : keySet) {
      String variable = variablePrefix + key + variablePostFix;
      input = input.replace(variable, variableSubs.getProperty(key));
    }
    return input;
  }
}
