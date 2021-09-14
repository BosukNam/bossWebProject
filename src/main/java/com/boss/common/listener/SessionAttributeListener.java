package com.boss.common.listener;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
@Component
public class SessionAttributeListener implements HttpSessionAttributeListener {
    public static ConcurrentHashMap<HttpSession, String> sessionMap = new ConcurrentHashMap<>();
    public void init(ServletConfig config) {
    }
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        String attributeName = event.getName();
        if(attributeName.equals("SPRING_SECURITY_CONTEXT")){
            if(event.getValue() instanceof SecurityContextImpl) {
                SecurityContextImpl securityContextImpl = (SecurityContextImpl) event.getValue();
                Authentication authentication =securityContextImpl.getAuthentication();
                String userId = authentication.getName();
                if(sessionMap.get(session) == null) {
                    sessionMap.put(session, userId);
                }
            }
        }
    }
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        HttpSession session = event.getSession();
        if (attributeName.equals("SPRING_SECURITY_CONTEXT")) {
            if(event.getValue() instanceof SecurityContextImpl) {
                if(sessionMap.get(session) != null) {
                    sessionMap.remove(session);
                }
            }
        }
    }
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
    }

    private void duplicateCheck(String userId) {
        ConcurrentHashMap<HttpSession, String> sessionMap = SessionAttributeListener.sessionMap;
        Enumeration<HttpSession> keys = sessionMap.keys();
        while (keys.hasMoreElements()) {
            HttpSession session = (HttpSession) keys.nextElement();
            if(sessionMap.get(session).equals(userId)){
                session.invalidate();
            }
        }
    }
}