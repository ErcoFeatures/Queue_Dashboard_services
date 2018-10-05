package com.spring.rest.webservices.restwebservices.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository  extends JpaRepository<Queue, Integer> {

}
