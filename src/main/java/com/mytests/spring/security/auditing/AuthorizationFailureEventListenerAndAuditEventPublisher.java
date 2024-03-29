package com.mytests.spring.security.auditing;

import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.security.AbstractAuthorizationAuditListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationFailureEventListenerAndAuditEventPublisher extends AbstractAuthorizationAuditListener {

    // no publishers are found: org.springframework.security.access.intercept.AbstractSecurityInterceptor.publishEvent() ?
    // hard to find? no issues are submitted
    @Override
    public void onApplicationEvent(AbstractAuthorizationEvent event) {
        if (event instanceof AuthorizationFailureEvent) {
            onAuthorizationFailureEvent((AuthorizationFailureEvent) event);
        }
    }

    public void onAuthorizationFailureEvent(AuthorizationFailureEvent event) {
        Map<String, Object> data = new HashMap<>();
        data.put("type", event.getAccessDeniedException().getClass().getName());
        data.put("message", event.getAccessDeniedException().getMessage());
        if (event.getSource() instanceof FilterInvocation)
            data.put("requestUrl", ((FilterInvocation)event.getSource()).getRequestUrl());
        else if (event.getSource() instanceof ReflectiveMethodInvocation)
            data.put("source", event.getSource());
        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        // no publisher gutter icons here: no issues are submitted; we shouldn't try to find all methods that call publishEvent() since
        // this is too expensive, and we can't be sure that these methods are used for publishing really?
        publish(new AuditEvent(event.getAuthentication().getName(), "auth failure",
                               data));
    }
}
