package com.ctrip.framework.apollo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LocalAdminServiceApplication {
  public static void main(String[] args) {
    new SpringApplicationBuilder(LocalAdminServiceApplication.class).run(args);
  }
}
