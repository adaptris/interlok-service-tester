package com.adaptris.taglet;

import com.sun.tools.doclets.Taglet;

import java.util.Map;

/**
 * @author mwarman
 */
public class ServiceTestTaglet extends AbstractTaglet {

  @Override
  public String getName() {
    return "service-test-config";
  }

  @Override
  public String getStart() {
    return " <p>In the service test configuration this class is aliased as <b><code>";
  }

  @Override
  public String getEnd() {
    return "</code></b> which is the preferred alternative to the fully qualified classname when building your configuration.</p>";
  }

  @SuppressWarnings("unchecked")
  public static void register(Map tagletMap) {
    ServiceTestTaglet tag = new ServiceTestTaglet();
    Taglet t = (Taglet) tagletMap.get(tag.getName());
    if (t != null) {
      tagletMap.remove(tag.getName());
    }
    tagletMap.put(tag.getName(), tag);
  }

}
