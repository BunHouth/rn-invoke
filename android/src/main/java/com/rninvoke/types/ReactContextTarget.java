package com.rninvoke.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wix.invoke.types.Target;

public abstract class ReactContextTarget extends Target {

    public ReactContextTarget(@JsonProperty("value") Object value) {
        super(value);
    }
}
