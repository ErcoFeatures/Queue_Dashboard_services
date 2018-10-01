package com.spring.rest.webservices.restwebservices.customer;

import com.spring.rest.webservices.restwebservices.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@ApiModel(description = "All details about the Queue")
@Entity
public class Queue {

    @Id
    @GeneratedValue
    private Integer id;
    @Size(min=2, message = "The Queue Label should have at least 2 characters")
    @ApiModelProperty(notes = "The Queue label should have at least 2 characters")
    private String label;

    @OneToMany(mappedBy = "queue")
    private List<Customer> customers;

    // not implemented now
    //    private User user;

    protected Queue(){

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

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
