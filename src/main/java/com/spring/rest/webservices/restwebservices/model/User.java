package com.spring.rest.webservices.restwebservices.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ApiModel(description = "All details about the user")
@Entity
public class User {


    @Id
    @GeneratedValue
    private Integer id;
    @Size(min=2, message = "Name should have at least 2 characters")
    @ApiModelProperty (notes = "Name should have at least 2 characters")
    private String name;
    @Past
    @ApiModelProperty(notes = "Birth date should be in the past")
    private Date birthDate;

    @ManyToMany
    @JoinTable(name = "member",
            joinColumns = { @JoinColumn(name = "fk_member") },
            inverseJoinColumns = { @JoinColumn(name = "fk_queue") })
    private Set<Queue> queues = new HashSet<Queue>();

    protected  User (){

    }
    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Queue> getQueues() {
        return queues;
    }

    public void setQueues(Set<Queue> queues) {
        this.queues = queues;
    }
    @Override
    public String toString() {
        return "com.spring.rest.webservices.restwebservices.entity.User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
