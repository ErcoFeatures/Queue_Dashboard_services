package com.spring.rest.webservices.restwebservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String phone_number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Queue queue;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
