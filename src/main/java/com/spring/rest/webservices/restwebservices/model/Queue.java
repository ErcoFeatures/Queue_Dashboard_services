package com.spring.rest.webservices.restwebservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "All details about the Queue")
@Entity
public class Queue {

    @Id
    @GeneratedValue
    private Integer id;
    @Size(min=2, message = "The Queue Label should have at least 2 characters")
    @ApiModelProperty(notes = "The Queue label should have at least 2 characters")
    private String label;

    @OneToMany
    @JoinColumn(name="queue_id", insertable = false, updatable = false)
    private Set<Member> members = new HashSet<Member>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    private Manager owner;

    protected Queue(){

    }

    public Manager getUser() {
        return owner;
    }

    public void setUser(Manager user) {
        this.owner = user;
    }

    public Queue(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
