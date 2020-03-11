package com.adaptris.tester.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ServiceTestExceptionTest {

  @Test
  public void testConstructors() {
    ServiceTestException e = new ServiceTestException();
    assertNull(e.getMessage());
    e = new ServiceTestException("fail");
    assertEquals("fail", e.getMessage());
    Exception nested = new Exception();
    e = new ServiceTestException(nested);
    assertSame(nested, e.getCause());
    e = new ServiceTestException("fail", nested);
    assertSame(nested, e.getCause());
    assertEquals("fail", e.getMessage());
  }

  @Test
  public void testWrapException() {
    ServiceTestException e = new ServiceTestException();
    assertSame(e, ServiceTestException.wrapException(e));
    assertNotEquals(e, ServiceTestException.wrapException(new Exception()));
  }

}
