package com.spring.rest.webservices.restwebservices.controller;


import com.spring.rest.webservices.restwebservices.Exception.ResourceNotNotFoundException;
import com.spring.rest.webservices.restwebservices.model.Member;
import com.spring.rest.webservices.restwebservices.model.Queue;
import com.spring.rest.webservices.restwebservices.repository.MemberRepository;
import com.spring.rest.webservices.restwebservices.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class MemberController {

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/users/{userId}/queues/{id}/members")
    public List<Member> retrieveAllQueuemembers(@PathVariable int id){
        Optional<Queue> queueOptional = queueRepository.findById(id);

        if((!queueOptional.isPresent())){
            throw  new ResourceNotNotFoundException("id-" +id);
        }
        return queueOptional.get().getMembers();
    }

    @PostMapping("/users/{userId}/queues/{id}/members")
    public ResponseEntity<Object> createMember(@PathVariable int id, @RequestBody Member member){
        Optional<Queue> queueOptional = queueRepository.findById(id);
        if(!queueOptional.isPresent()){
            throw  new ResourceNotNotFoundException(("id-" +id));
        }
        Queue queue = queueOptional.get();
        member.setQueue(queue);
        memberRepository.save(member);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(member.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
