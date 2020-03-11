package com.adaptris.tester.runtime.clients;

import com.adaptris.tester.runtime.services.ServiceToTest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Extension of {@link ServiceToTest} purely for XStreamAlias purposes.
 *
 * @service-test-config service-provider
 */
@XStreamAlias("service-provider")
public class ServiceProvider extends ServiceToTest {
}
