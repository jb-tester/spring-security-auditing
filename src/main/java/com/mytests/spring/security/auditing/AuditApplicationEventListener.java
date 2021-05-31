package com.mytests.spring.security.auditing;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditApplicationEventListener {

    // duplicated variants in gutter icon navigation popup: org.springframework.boot.actuate.security.AbstractAuthenticationAuditListener.publish
    @EventListener
    public void onAuditAppEvent(AuditApplicationEvent auditApplicationEvent) {
        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();
        System.out.println("****** onAuditAppEvent() method ******");
        System.out.println("****** AuditEvent has happened ******");
        System.out.println(auditEvent.getType());
        System.out.println(auditEvent.getPrincipal());
        System.out.println(auditEvent.getData());
        System.out.println("**************************************");

    }
    @EventListener
    public void onAuditEvent(AuditEvent auditEvent) {
        System.out.println("****** onAuditEvent() method ******");
        System.out.println("****** AuditEvent has happened ******");
        System.out.println(auditEvent.getType());
        System.out.println(auditEvent.getPrincipal());
        System.out.println(auditEvent.getData());
        System.out.println("**************************************");

    }

}
