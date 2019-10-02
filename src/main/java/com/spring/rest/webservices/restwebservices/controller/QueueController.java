package com.spring.rest.webservices.restwebservices.controller;


import com.spring.rest.webservices.restwebservices.Exception.ResourceNotNotFoundException;
import com.spring.rest.webservices.restwebservices.model.Queue;
import com.spring.rest.webservices.restwebservices.model.User;
import com.spring.rest.webservices.restwebservices.repository.QueueRepository;
import com.spring.rest.webservices.restwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class QueueController {

    @Autowired
    private QueueRepository queueRepository;


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{id}/queues")
    public Set<Queue> retrieveAllUserQueues(@PathVariable Long id ) {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new ResourceNotNotFoundException("id-" + id);
        }
        return userOptional.get().getQueues();
    }
    @GetMapping("/queues")
        public List<Queue> retrieveAllQueue() {
        List<Queue> allQueues = queueRepository.findAll();

            if (allQueues.isEmpty()) {
                throw new ResourceNotNotFoundException("There is no Queue");
            }
            return queueRepository.findAll();
        }

    @PostMapping("/users/{id}/queues")
    public ResponseEntity<Object> createQueue(@PathVariable Long id, @RequestBody Queue queue) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new ResourceNotNotFoundException("id-" + id);
        }
        User user = userOptional.get();

        queue.setOwner(user);
        queueRepository.save(queue);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(queue.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{userId}/queues/{id}")
    public Resource<Queue> retrieveQueue(@PathVariable int id, @PathVariable Long userId) {
        Optional<Queue> queue = queueRepository.findById(id);
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotNotFoundException("id-" + userId);
        }
        if (!queue.isPresent()) {
            throw new ResourceNotNotFoundException("id-" + id);
        }

        Resource<Queue> resource = new Resource<>(queue.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUserQueues(userId));
        resource.add(linkTo.withRel("all-queues"));

        return resource;

    }

    @DeleteMapping("/users/{userId}/queues/{id}")
    public void deleteQueue(@PathVariable int id) {
        queueRepository.deleteById(id);
    }


    // member ressource


    @GetMapping("/queues/{id}/members")
    public Set<User> retrieveAllQueueMembers(@PathVariable int id){
        Optional<Queue> queueOptional = queueRepository.findById(id);

        if((!queueOptional.isPresent())){
            throw  new ResourceNotNotFoundException("id-" +id);
        }
        return queueOptional.get().getMembers();
    }

    @PostMapping("/queues/{id}/members/{m_id}")
    public ResponseEntity<Object> AddQueueMember(@PathVariable int id, @PathVariable Long m_id){
        Optional<Queue> queueOptional = queueRepository.findById(id);
        Optional<User> userOptional = userRepository.findById(m_id);
        if(!queueOptional.isPresent() || !userOptional.isPresent()){
            throw  new ResourceNotNotFoundException(("id-" +id +" id-"+m_id));
        }
        Queue queue = queueOptional.get();
        User member = userOptional.get();
        queue.getMembers().add(member);
        userRepository.save(member);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(member.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


}
