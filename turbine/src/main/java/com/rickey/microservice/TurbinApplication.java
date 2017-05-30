package com.rickey.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * Hello world!
 *
 */
@EnableTurbineStream
@SpringBootApplication
public class TurbinApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(TurbinApplication.class, args);
    }
}
