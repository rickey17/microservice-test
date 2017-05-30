package com.rickey.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ZipkinApplication.class, args);
    }
}
