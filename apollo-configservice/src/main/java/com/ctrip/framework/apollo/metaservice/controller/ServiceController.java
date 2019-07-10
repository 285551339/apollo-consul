package com.ctrip.framework.apollo.metaservice.controller;

import com.ctrip.framework.apollo.core.dto.ServiceDTO;
import com.ctrip.framework.apollo.metaservice.service.DiscoveryService;
import com.ecwid.consul.v1.health.model.HealthService;
//import com.netflix.appinfo.InstanceInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/services")
public class ServiceController {

  private final DiscoveryService discoveryService;

  public ServiceController(final DiscoveryService discoveryService) {
    this.discoveryService = discoveryService;
  }


  @RequestMapping("/meta")
  public List<ServiceDTO> getMetaService() {
   List<HealthService> instances = discoveryService.getMetaServiceInstances();
    List<ServiceDTO> result = instances.stream().map(new Function<HealthService, ServiceDTO>() {
      @Override
      public ServiceDTO apply(HealthService instance) {
        ServiceDTO service = new ServiceDTO();
        service.setAppName(instance.getService().getId());
        service.setInstanceId(null);
        service.setHomepageUrl(instance.getService().getAddress() + ":" + instance.getService().getPort());
        return service;
      }
    }).collect(Collectors.toList());
    return result;
  }

  @RequestMapping("/config")
  public List<ServiceDTO> getConfigService(
          @RequestParam(value = "appId", defaultValue = "") String appId,
          @RequestParam(value = "ip", required = false) String clientIp) {
    List<HealthService> instances = discoveryService.getConfigServiceInstances();
    List<ServiceDTO> result = instances.stream().map(new Function<HealthService, ServiceDTO>() {
      @Override
      public ServiceDTO apply(HealthService instance) {
        ServiceDTO service = new ServiceDTO();
        service.setAppName(instance.getService().getId());
        service.setInstanceId(null);
        service.setHomepageUrl("http://"+instance.getService().getAddress() + ":" + instance.getService().getPort());
        return service;
      }
    }).collect(Collectors.toList());
    return result;
  }

  @RequestMapping("/admin")
  public List<ServiceDTO> getAdminService() {
    List<HealthService> instances = discoveryService.getAdminServiceInstances();
    List<ServiceDTO> result = instances.stream().map(new Function<HealthService, ServiceDTO>() {
      @Override
      public ServiceDTO apply(HealthService instance) {
        ServiceDTO service = new ServiceDTO();
        service.setAppName(instance.getService().getId());
        service.setInstanceId(null);
        service.setHomepageUrl("http://"+instance.getService().getAddress() + ":" + instance.getService().getPort());
        return service;
      }
    }).collect(Collectors.toList());
    return result;
  }
}
