package com.rickey.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class UploadApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(UploadApplication.class, args);
    }
}
