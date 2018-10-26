package com.spring.rest.webservices.restwebservices.repository;

import com.spring.rest.webservices.restwebservices.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRespository  extends JpaRepository<Manager ,Integer> {


}
