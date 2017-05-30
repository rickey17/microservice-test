package com.rickey.microservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rickey.microservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

/**
 * Created by Rickey_17 on 17-5-29.
 */
@Service
public class AggregationService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getUserById(int id){
        return Observable.create(observer -> {
            User user = restTemplate.getForObject("http://microservice-provider-user/{id}",
                    User.class,
                    id);
            observer.onNext(user);
            observer.onCompleted();
        });
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getMovieUserById(int id){
        return Observable.create(observer -> {
            User user = restTemplate.getForObject("http://microservice-consumer-movie/{id}",
                    User.class,
                    id);
            observer.onNext(user);
            observer.onCompleted();
        });
    }

    public User fallback(int id){
        User user = new User();
        user.setId(-1);
        user.setName("游客");
        user.setName("游客");
        return user;
    }
}
