package com.mytests.spring.security.auditing;

import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * *
 * <p>Created by irina on 31.05.2021.</p>
 * <p>Project: spring-security-auditing</p>
 * *
 */
@Component
public class AuthorizationSuccessEventListenerAndAuditMessagePublisher {

    @Autowired  ApplicationEventPublisher publisher;

    @EventListener   // https://youtrack.jetbrains.com/issue/IDEA-270495: no publishers are found until sources are downloaded: org.springframework.security.authentication.DefaultAuthenticationEventPublisher.publishAuthenticationSuccess()?
    public void onAuthSuccessEvent(AuthenticationSuccessEvent event) {
        String name = event.getAuthentication().getName();
        Map<String, Object> data = new HashMap<>();
        if (event.getSource() instanceof FilterInvocation)
            data.put("requestUrl", ((FilterInvocation)event.getSource()).getRequestUrl());
        else if (event.getSource() instanceof ReflectiveMethodInvocation)
            data.put("source", event.getSource());
        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        publishAuditEvent(data, name);
    }

    private void publishAuditEvent(Map<String, Object> data, String name) {
        publisher.publishEvent(new AuditEvent(name, "auth success", data));

    }
}
