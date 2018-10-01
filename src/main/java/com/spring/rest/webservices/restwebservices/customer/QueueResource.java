package com.spring.rest.webservices.restwebservices.customer;


import com.spring.rest.webservices.restwebservices.Exception.ResourceNotNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class QueueResource {

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/queues")
    public List<Queue> retrieveAllQueues (){
        return queueRepository.findAll();
    }
    @GetMapping("/queues/{id}")
    public Resource <Queue> retrieveQueue(@PathVariable int id){
        Optional<Queue> queue = queueRepository.findById(id);
        if (!queue.isPresent()) {
            throw new ResourceNotNotFoundException("id-" + id);
        }

        Resource<Queue> resource = new Resource<>(queue.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllQueues());
        resource.add(linkTo.withRel("all-queues"));

        return  resource;

    }

    @DeleteMapping("/queues/{id}")
    public void deleteQueue(@PathVariable int id){
        queueRepository.deleteById(id);
    }

    @PostMapping("/queues")
    public ResponseEntity<Object> createQueue (@Valid @RequestBody Queue queue){
        Queue savedQueue = queueRepository.save(queue);

        URI location  = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedQueue.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/queues/{id}/customers")
    public List<Customer> retrieveAllQueueCustomers(@PathVariable int id){
        Optional<Queue> queueOptional = queueRepository.findById(id);

        if((!queueOptional.isPresent())){
            throw  new ResourceNotNotFoundException("id-" +id);
        }
        return queueOptional.get().getCustomers();
    }

    @PostMapping("/queues/{id}/customers")
    public ResponseEntity<Object> createCustomer(@PathVariable int id, @RequestBody Customer customer){
        Optional<Queue> queueOptional = queueRepository.findById(id);
        if(!queueOptional.isPresent()){
            throw  new ResourceNotNotFoundException(("id-" +id));
        }
        Queue queue = queueOptional.get();
        customer.setQueue(queue);
        customerRepository.save(customer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
