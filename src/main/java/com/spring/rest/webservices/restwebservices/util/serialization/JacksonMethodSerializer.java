package com.spring.rest.webservices.restwebservices.util.serialization;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.spring.rest.webservices.restwebservices.annotation.ExcludeFieldinGson;

public class JacksonMethodSerializer extends JacksonAnnotationIntrospector {

    private static final long serialVersionUID = 1L;

    @Override
    protected boolean _isIgnorable(Annotated a) {
        boolean isIgnorable = super._isIgnorable(a);
        if (!isIgnorable) {
            ExcludeFieldinGson ann = a.getAnnotation(ExcludeFieldinGson.class);
            isIgnorable = ann != null;
        }
        return isIgnorable;
    }
}