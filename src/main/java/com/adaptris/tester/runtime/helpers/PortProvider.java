package com.adaptris.tester.runtime.helpers;

/**
 * Base interface for port providers.
 *
 * <p>Port providers are to be used with {@link Helper} implementations.</p>
 */
public interface PortProvider {

  void initPort();

  int getPort();

  void releasePort();
}
