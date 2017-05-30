package com.rickey.microservice.feign;

import com.rickey.microservice.pojo.User;
import feign.Param;
import feign.RequestLine;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by Rickey_17 on 17-5-13.
 */
@FeignClient(name = "microservice-provider-user", fallbackFactory = FeignClientFallbackFactory.class)
public interface UserFeignClient {

    @RequestLine("GET /{id}")
    User findById(@Param("id") int id);
}

class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient>{

    private static final Logger logger = LoggerFactory.getLogger(FeignClientFallbackFactory.class);

    public UserFeignClient create(final Throwable throwable) {
        return new UserFeignClient() {
            public User findById(@Param("id") int id) {
                logger.error("fallback; reason was:", throwable);
                User user = new User();
                user.setUserName("游客");
                user.setName("游客");
                if(throwable instanceof IllegalArgumentException)
                    user.setId(-2);
                else
                    user.setId(-1);
                return user;
            }
        };
    }
}