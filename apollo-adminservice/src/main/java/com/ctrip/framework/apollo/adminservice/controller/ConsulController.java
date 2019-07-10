package com.ctrip.framework.apollo.adminservice.controller;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping("/consul")
public class ConsulController {
    private static Logger logger = LoggerFactory.getLogger(ConsulController.class);
    private static String startedAt = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LoadBalancerClient loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${project.build.timestamp:2019-07-08 20:00:00}")
    private String buildTimestamp;


    @GetMapping("/health")
    public String health() throws Exception {
        String h = "{\"status\":\"UP\"}";
        return  h ;
        //return "start at " + startedAt + ", build at " + DateUtils.utc2Cst(buildTimestamp) + ", check at " + DateUtils.formatDateTime(new Date());
    }


    @GetMapping("/client")
    public String connect() {
        return "ok";
    }

    /**
     * 获取所有服务
     */
    @RequestMapping("/services")
    public Object services() {
        return discoveryClient.getInstances("netcafe-pubwin");
    }

    /**
     * 从所有服务中选择一个服务（轮询）
     */
    @RequestMapping("/discover")
    public Object discover() {
        return loadBalancer.choose("netcafe-pubwin").getUri().toString();
    }

}
