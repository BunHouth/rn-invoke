package com.rninvoke.types;

import android.view.View;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wix.invoke.MethodUtilsExt;
import com.wix.invoke.types.Invocation;
import com.rninvoke.ContextWrapper;

/**
 * Created by rotemm on 25/10/2016.
 */

public class ReactViewTarget extends ReactContextTarget {

    @JsonCreator
    public ReactViewTarget(@JsonProperty("value") final Object value) {
        super(value);
    }

    @Override
    public Object execute(Invocation invocation) throws Exception {
        View view = ContextWrapper.getNativeViewHierarchyManager().resolveView(((Double)getValue()).intValue());
        return MethodUtilsExt.invokeExactMethodNoAutobox(view, invocation.getMethod(), invocation.getArgs());
    }
}
