package com.spring.rest.webservices.restwebservices.controller;


import com.spring.rest.webservices.restwebservices.Exception.ResourceNotNotFoundException;
import com.spring.rest.webservices.restwebservices.model.Queue;
import com.spring.rest.webservices.restwebservices.repository.QueueRepository;
import com.spring.rest.webservices.restwebservices.model.User;
import com.spring.rest.webservices.restwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QueueRepository queueRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();

    }
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}")
    public Resource<User>  retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ResourceNotNotFoundException("id-" + id);
        }

        // "all-users", SERVER_PATH + "/users"

        Resource<User> resource = new Resource<User>(user.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));
        // Use HATEOAS
        return resource;

    }
    @DeleteMapping("/users/{id}")
    public void  deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

}
