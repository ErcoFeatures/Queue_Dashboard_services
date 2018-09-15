package com.spring.rest.webservices.restwebservices.filtering;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {


    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean(){
        SomeBean someBean  = new SomeBean("Value 1","Value 2","Value 3");
        MappingJacksonValue mapping = getMappingValue(someBean);
        return mapping;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListOfSomeBean(){
        List<SomeBean> list =  Arrays.asList(new SomeBean("Value 1","Value 2","Value 3"),new SomeBean("Value 12","Value 22","Value 32"));

        MappingJacksonValue mapping = getMappingValue(list);
        return mapping;
    }

    private MappingJacksonValue getMappingValue(Object obj) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept().filterOutAllExcept("field2", "field3");
        FilterProvider filters  = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(obj);
        mapping.setFilters(filters);
        return mapping;
    }


}
