package com.rickey.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ProviderUserApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProviderUserApplication.class, args);
    }
}
