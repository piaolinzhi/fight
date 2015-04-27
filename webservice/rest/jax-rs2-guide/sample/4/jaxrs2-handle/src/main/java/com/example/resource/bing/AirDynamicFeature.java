package com.example.resource.bing;

import javax.ws.rs.POST;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import com.example.resource.BookResource;

public class AirDynamicFeature implements DynamicFeature {

    @Override
    public void configure(final ResourceInfo resourceInfo, final FeatureContext context) {
        boolean classMatched = BookResource.class.isAssignableFrom(resourceInfo.getResourceClass());
        boolean methodNameMatched = resourceInfo.getResourceMethod().getName().contains("getBookBy");
        boolean methodTypeMatched = resourceInfo.getResourceMethod().isAnnotationPresent(POST.class);
        if (classMatched && (methodNameMatched || methodTypeMatched)) {
            context.register(AirDynamicBindingFilter.class);
        }
    }
}
