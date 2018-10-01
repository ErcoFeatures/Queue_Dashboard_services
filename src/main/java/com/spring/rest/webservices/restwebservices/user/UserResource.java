package com.spring.rest.webservices.restwebservices.user;


import com.spring.rest.webservices.restwebservices.Exception.ResourceNotNotFoundException;
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
    private PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();

    }

    @GetMapping("/jpa/users/{id}")
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
    @DeleteMapping("/jpa/users/{id}")
    public void  deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllUserPosts(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw  new ResourceNotNotFoundException("id-" +id);
        }


        return userOptional.get().getPosts();
    }
    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post  post) {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw  new ResourceNotNotFoundException("id-" +id);
        }
        User user = userOptional.get();

        post.setUser(user);
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
