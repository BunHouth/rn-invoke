package com.rninvoke.types;

/**
 * Created by rotemm on 02/01/2017.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by rotemm on 25/10/2016.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ReactContextTarget.class, name = "React.Context"),
        @JsonSubTypes.Type(value = ReactViewTarget.class, name = "React.View")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class TargetExtendMixIn {

}
