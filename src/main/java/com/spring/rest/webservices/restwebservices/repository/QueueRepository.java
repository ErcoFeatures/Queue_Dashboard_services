package com.spring.rest.webservices.restwebservices.repository;

import com.spring.rest.webservices.restwebservices.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository  extends JpaRepository<Queue, Integer> {

}
