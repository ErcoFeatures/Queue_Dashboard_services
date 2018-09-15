package com.spring.rest.webservices.restwebservices.user;


import com.spring.rest.webservices.restwebservices.Exception.UserNotNotFoundException;
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

@RestController
public class userResource {

    @Autowired
    private UserDaoService service;


    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();

    }

    @GetMapping("/users/{id}")
    public Resource<User>  retrieveUsers(@PathVariable int id) {
        User user = service.getUserById(id);
        if (user == null) {
            throw new UserNotNotFoundException("id-" + id);
        }

        // "all-users", SERVER_PATH + "/users"

        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));
        // Use HATEOAS
        return resource;

    }
    @DeleteMapping("/users/{id}")
    public void  deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);
        if (user == null) {
            throw new UserNotNotFoundException("id-" + id);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
