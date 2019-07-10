package com.ctrip.framework.apollo.metaservice.service;

import com.ctrip.framework.apollo.core.ServiceNameConsts;
import com.ctrip.framework.apollo.tracer.Tracer;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DiscoveryService {

  @Autowired
  private ConsulClient consulClient;


  public List<HealthService> getConfigServiceInstances() {
    // Application application = eurekaClient.getApplication(ServiceNameConsts.APOLLO_CONFIGSERVICE);
    Response<List<HealthService>> healthyServices = consulClient
            .getHealthServices(ServiceNameConsts.APOLLO_CONFIGSERVICE, true, QueryParams.DEFAULT);
    List<HealthService> result = healthyServices.getValue();
    if (healthyServices == null) {
      Tracer.logEvent("Apollo.ConsulDiscovery.NotFound", ServiceNameConsts.APOLLO_CONFIGSERVICE);
    }
    return result != null ? result : Collections.emptyList();
  }

  public List<HealthService> getMetaServiceInstances() {
    //Application application = eurekaClient.getApplication(ServiceNameConsts.APOLLO_METASERVICE);
    Response<List<HealthService>> healthyServices = consulClient
            .getHealthServices(ServiceNameConsts.APOLLO_METASERVICE, true, QueryParams.DEFAULT);
    List<HealthService> result = healthyServices.getValue();
    if (healthyServices == null) {
      Tracer.logEvent("Apollo.ConsulDiscovery.NotFound", ServiceNameConsts.APOLLO_CONFIGSERVICE);
    }
    return result != null ? result : Collections.emptyList();
  }

  public List<HealthService> getAdminServiceInstances() {
    //Application application = eurekaClient.getApplication(ServiceNameConsts.APOLLO_ADMINSERVICE);
    Response<List<HealthService>> healthyServices = consulClient
            .getHealthServices(ServiceNameConsts.APOLLO_ADMINSERVICE, true, QueryParams.DEFAULT);
    List<HealthService> result = healthyServices.getValue();
    if (healthyServices == null) {
      Tracer.logEvent("Apollo.ConsulDiscovery.NotFound", ServiceNameConsts.APOLLO_CONFIGSERVICE);
    }
    return result != null ? result : Collections.emptyList();
  }
}
