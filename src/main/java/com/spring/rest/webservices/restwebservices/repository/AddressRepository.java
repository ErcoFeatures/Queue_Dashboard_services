package com.spring.rest.webservices.restwebservices.repository;

import com.spring.rest.webservices.restwebservices.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository  extends JpaRepository<Address, Integer> {

}
