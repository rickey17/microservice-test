package com.rickey.microservice.repository;


import com.rickey.microservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Rickey_17 on 17-5-13.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Serializable> {
}
