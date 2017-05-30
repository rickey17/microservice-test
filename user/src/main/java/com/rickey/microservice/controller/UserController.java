package com.rickey.microservice.controller;

import com.rickey.microservice.model.User;
import com.rickey.microservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Rickey_17 on 17-5-13.
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/{id}")
    public User findById(@PathVariable int id){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(principal instanceof UserDetails){
//            UserDetails user = (UserDetails) principal;
//            Collection<? extends GrantedAuthority> collection = user.getAuthorities();
//            for(GrantedAuthority c : collection){
//                logger.info("当前用户是{},角色是{}", user.getUsername(), c.getAuthority());
//            }
//        }else{
//            logger.info("用户不存在");
//        }
        return userService.getById(id);
    }

    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo(){
        return discoveryClient.getInstances("microservice-provider-user");
    }
}
