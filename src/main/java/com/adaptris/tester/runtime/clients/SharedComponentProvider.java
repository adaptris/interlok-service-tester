package com.adaptris.tester.runtime.clients;

import com.adaptris.annotation.AutoPopulated;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to add {@link com.adaptris.core.Service} to {@link EmbeddedTestClient}.
 *
 * @service-test-config shared-components-provider
 */
@XStreamAlias("shared-components-provider")
public class SharedComponentProvider {

  @Valid
  @AutoPopulated
  @NotNull
  private List<ServiceProvider> services;

  public SharedComponentProvider(){
    setServices(new ArrayList<>());
  }

  public List<ServiceProvider> getServices() {
    return services;
  }

  public void setServices(List<ServiceProvider> services) {
    this.services = new ArrayList<>();
    for(ServiceProvider service : services){
      addService(service);
    }
  }

  void addService(ServiceProvider service){
    getServices().add(service);
  }
}
