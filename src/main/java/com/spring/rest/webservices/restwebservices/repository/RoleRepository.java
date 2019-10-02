package com.spring.rest.webservices.restwebservices.repository;

import com.spring.rest.webservices.restwebservices.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(String role);
}
