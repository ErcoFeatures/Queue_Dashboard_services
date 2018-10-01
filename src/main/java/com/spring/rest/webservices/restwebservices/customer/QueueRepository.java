package com.spring.rest.webservices.restwebservices.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface QueueRepository  extends JpaRepository<Queue, Integer> {

}