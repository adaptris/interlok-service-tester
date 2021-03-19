package com.adaptris.tester.runtime;

/**
 * Base interface for all test components with uniqueId.
 */
public interface UniqueIdAwareTestComponent extends TestComponent {

  /**
   * Return the unique id.
   *
   * @return The unique id
   */
  String getUniqueId();
}
