package com.spring.rest.webservices.restwebservices.user;


import com.spring.rest.webservices.restwebservices.Exception.ResourceNotNotFoundException;
import com.spring.rest.webservices.restwebservices.member.Queue;
import com.spring.rest.webservices.restwebservices.member.QueueRepository;
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
public class UserResource {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QueueRepository queueRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();

    }

    @GetMapping("/users/{id}")
    public Resource<User>  retrieveUsers(@PathVariable int id) {
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

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/users/{id}/Queues")
    public List<Queue> retrieveAllUserQueues(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw  new ResourceNotNotFoundException("id-" +id);
        }


        return userOptional.get().getQueues();
    }
    @PostMapping("/users/{id}/Queues")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Queue  queue) {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw  new ResourceNotNotFoundException("id-" +id);
        }
        User user = userOptional.get();

        queue.setUser(user);
        queueRepository.save(queue);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(queue.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
