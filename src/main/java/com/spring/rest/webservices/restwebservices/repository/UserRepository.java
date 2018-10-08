package com.spring.rest.webservices.restwebservices.repository;


import com.spring.rest.webservices.restwebservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

}
