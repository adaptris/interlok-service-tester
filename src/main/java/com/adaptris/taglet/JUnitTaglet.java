package com.adaptris.taglet;

import com.sun.tools.doclets.Taglet;

import java.util.Map;

/**
 * @author mwarman
 */
public class JUnitTaglet extends AbstractTaglet {

  @Override
  public String getName() {
    return "junit-config";
  }

  @Override
  public String getStart() {
    return " <p>In the result output file this class is aliased as <b><code>";
  }

  @Override
  public String getEnd() {
    return "</code></b>.</p>";
  }

  @SuppressWarnings("unchecked")
  public static void register(Map tagletMap) {
    JUnitTaglet tag = new JUnitTaglet();
    Taglet t = (Taglet) tagletMap.get(tag.getName());
    if (t != null) {
      tagletMap.remove(tag.getName());
    }
    tagletMap.put(tag.getName(), tag);
  }

}
