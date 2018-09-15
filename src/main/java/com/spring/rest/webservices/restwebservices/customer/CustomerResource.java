package com.spring.rest.webservices.restwebservices.customer;


import com.spring.rest.webservices.restwebservices.Exception.UserNotNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CustomerResource {

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> retrieveAllCustomers (){
        return customerRepository.findAll();
    }
    @GetMapping("/customers/{id}")
    public Resource <Customer> retrieveCustomer(@PathVariable int id){
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new UserNotNotFoundException("id-" + id);
        }

        Resource<Customer> resource = new Resource<>(customer.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllCustomers());
        resource.add(linkTo.withRel("all-users"));

        return  resource;

    }
}
