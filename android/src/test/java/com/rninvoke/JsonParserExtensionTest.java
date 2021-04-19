package com.rninvoke;

import com.wix.invoke.parser.JsonParser;
import com.wix.invoke.types.Invocation;
import com.wix.invoke.types.Target;
import com.wix.reactnativeinvoke.types.ReactViewTarget;
import com.wix.reactnativeinvoke.types.TargetExtendMixIn;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by rotemm on 13/10/2016.
 */
public class JsonParserExtensionTest {

    @Test
    public void targetReactViewResolveFromId() {
        Invocation invocation = new Invocation(new ReactViewTarget("123456"), "testMethod");
        assertThat(parse("targetReactViewResolveFromId.json")).isEqualToComparingFieldByFieldRecursively(invocation);
    }


    public Invocation parse(String filePath) {
        String jsonData = TestUtils.jsonFileToString(filePath);
        JsonParser parser = new JsonParser();
        parser.addMixInAnnotations(Target.class, TargetExtendMixIn.class);
        return parser.parse(jsonData, Invocation.class);
    }
}