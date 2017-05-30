package com.rickey.microservice.controller;

import com.google.common.collect.Maps;
import com.rickey.microservice.model.User;
import com.rickey.microservice.service.AggregationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

import java.util.HashMap;

/**
 * Created by Rickey_17 on 17-5-29.
 */
@RestController
public class AggregationController {

    private static final Logger logger = LoggerFactory.getLogger(AggregationController.class);

    @Autowired
    private AggregationService aggregationService;

    @GetMapping("/aggregate/{id}")
    public DeferredResult<HashMap<String, User>> aggregate(@PathVariable int id){
        Observable<HashMap<String, User>> result = this.aggregateObservable(id);
        return this.toDeferredResult(result);
    }

    public Observable<HashMap<String, User>> aggregateObservable(int id){
        return Observable.zip(
                this.aggregationService.getUserById(id),
                this.aggregationService.getMovieUserById(id),
                (user, movieUser) -> {
                    HashMap<String, User> map = Maps.newHashMap();
                    map.put("user", user);
                    map.put("movieUser", movieUser);
                    return map;
                }
        );
    }

    public DeferredResult<HashMap<String, User>> toDeferredResult(Observable<HashMap<String, User>> details){
        DeferredResult<HashMap<String, User>> result = new DeferredResult<>();
        details.subscribe(
                new Observer<HashMap<String, User>>() {
                    @Override
                    public void onCompleted() {
                        logger.info("完成");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        logger.info("错误", throwable);
                    }

                    @Override
                    public void onNext(HashMap<String, User> stringUserHashMap) {
                        result.setResult(stringUserHashMap);
                    }
                }
        );
        return result;
    }
}
