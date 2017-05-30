package com.rickey.microservice.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rickey.microservice.feign.UserFeignClient;
import com.rickey.microservice.pojo.User;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Rickey_17 on 17-5-13.
 */
@Import(FeignClientsConfiguration.class)
@RestController
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private UserFeignClient userFeignClient;

    private UserFeignClient adminFeignClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    public MovieController(Decoder decoder, Encoder encoder, Client client, Contract contract){
        this.userFeignClient = Feign.builder().client(client).
                encoder(encoder).
                decoder(decoder).
                contract(contract).
                requestInterceptor(new BasicAuthRequestInterceptor("user", "pwd1")).
                target(UserFeignClient.class, "http://microservice-provider-user/");

        this.adminFeignClient = Feign.builder().client(client).
                encoder(encoder).
                decoder(decoder).
                contract(contract).
                requestInterceptor(new BasicAuthRequestInterceptor("admin", "pwd2")).
                target(UserFeignClient.class, "http://microservice-provider-user/");
    }

    @HystrixCommand(fallbackMethod = "findByIdFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "2000")
        }, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value="1"),
            @HystrixProperty(name = "maxQueueSize", value="5")
        }
    )
    @GetMapping("/{id}")
    public User findById(@PathVariable int id){
        return restTemplate.getForObject("http://microservice-provider-user/"+id, User.class);
//        return userFeignClient.findById(id);
    }

    public User findByIdFallback(int id){
        User user = new User();
        user.setId(-1);
        user.setUserName("游客");
        user.setName("");
        return user;
    }

    @GetMapping("/admin/{id}")
    public User findByIdAdmin(@PathVariable int id){
        return adminFeignClient.findById(id);
    }

    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo(){
        return discoveryClient.getInstances("microservice-consumer-movie");
    }

    @GetMapping("/log-instance")
    public void logInstance(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("microservice-consumer-movie");
        logger.info("{}:{}:{}",
                serviceInstance.getServiceId(),
                serviceInstance.getHost(),
                serviceInstance.getPort());
    }
}
