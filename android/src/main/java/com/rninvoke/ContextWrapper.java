package com.rninvoke;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.NativeViewHierarchyManager;

import java.lang.ref.WeakReference;

/**
 * Created by rotemm on 25/10/2016.
 */

public class ContextWrapper {

    private static WeakReference<ReactApplicationContext> sReactContext;
    private static WeakReference<NativeViewHierarchyManager> sNativeViewHierarchyManager;
    public static void init(ReactApplicationContext reactContext) {
        sReactContext = new WeakReference<>(reactContext);
    }

    public static ReactApplicationContext getReactApplicationContext() {
        if (sReactContext == null) {
            throw new RuntimeException("ContextWrapper needs to be initialized using init(context)");
        }
        return sReactContext.get();
    }

    public static void setNativeViewHierarchyManager(NativeViewHierarchyManager nativeViewHierarchyManager) {
        sNativeViewHierarchyManager = new WeakReference<>(nativeViewHierarchyManager);
    }

    public static NativeViewHierarchyManager getNativeViewHierarchyManager() {
        if (sNativeViewHierarchyManager == null) {
            throw new RuntimeException("NativeViewHierarchyManager has not yet been set");
        }
        return sNativeViewHierarchyManager.get();
    }
}
