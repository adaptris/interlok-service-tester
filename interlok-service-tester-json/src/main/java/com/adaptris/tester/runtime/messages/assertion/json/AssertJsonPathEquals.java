package com.adaptris.tester.runtime.messages.assertion.json;

import java.util.EnumSet;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.messages.assertion.Assertion;
import com.adaptris.tester.runtime.messages.assertion.AssertionResult;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.spi.json.JsonSmartJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Assert that a JSON Path resolves to some value.
 * 
 * @service-test-config assert-jsonpath-equals
 *
 */
@XStreamAlias("assert-jsonpath-equals")
public class AssertJsonPathEquals implements Assertion {

  private static final String ASSERT_JSONPATH_EQUALS = "assert-jsonpath-equals";
  private String jsonPath;
  private String value;

  private transient Configuration jsonConfig = new Configuration.ConfigurationBuilder().jsonProvider(new JsonSmartJsonProvider())
      .mappingProvider(new JacksonMappingProvider()).options(EnumSet.noneOf(Option.class)).build();


  public AssertJsonPathEquals() {

  }

  @Override
  public AssertionResult execute(TestMessage actual, ServiceTestConfig config) throws ServiceTestException {
    try {
      ReadContext context = JsonPath.parse(actual.getPayload(), jsonConfig);
      String xpathResult = context.read(getJsonPath());
      String message = String.format("Assertion Failure: [%s] Expected [%s] Returned [%s]", ASSERT_JSONPATH_EQUALS, getValue(), xpathResult);
      return new AssertionResult(ASSERT_JSONPATH_EQUALS, getValue().equals(xpathResult), message);
    } catch (Exception e) {
      throw ServiceTestException.wrapException(e);
    }
  }

  @Override
  public String expected() {
    return "Value [" + getValue() + "] at JSONPath [" + getJsonPath() + "]";
  }

  @Override
  public boolean showReturnedMessage() {
    return true;
  }

  public String getJsonPath() {
    return jsonPath;
  }

  public void setJsonPath(String jsonPath) {
    this.jsonPath = jsonPath;
  }

  public AssertJsonPathEquals withJsonPath(String s) {
    setJsonPath(s);
    return this;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public AssertJsonPathEquals withValue(String s) {
    setValue(s);
    return this;
  }

}
