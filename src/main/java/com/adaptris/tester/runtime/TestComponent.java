package com.adaptris.tester.runtime;

/**
 * Base interface for all test components.
 */
public interface TestComponent {

  /**
   * Return the unique id.
   * @return The unique id
   */
  String getUniqueId();
}
