package com.boss.common.listener;

import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionAttributeListener implements HttpSessionAttributeListener {
    public static ConcurrentHashMap<String, HttpSession> sessionMap = new ConcurrentHashMap<>();
    public void init(ServletConfig config) {
    }
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        String attributeName = event.getName();

        if("SPRING_SECURITY_CONTEXT".equals(attributeName)){
            if(event.getValue() instanceof SecurityContextImpl) {
                SecurityContextImpl securityContextImpl = (SecurityContextImpl) event.getValue();
                Authentication authentication =securityContextImpl.getAuthentication();
                String userId = authentication.getName();
                if(sessionMap.get(userId)==null) {
                    sessionMap.put(userId, session);
                } else if(sessionMap.get(userId)!=null){
                    // TODO : 기존 로그인 유저를 invalid 할 것인지, 신규 로그인 유저를 invalid 할 것인지 등 고민
                    session.invalidate();
                    try {
                        sessionMap.get(userId).invalidate();
                    } catch (NullPointerException ex) {
                        log.error("session invalidated already!");
                    }
                }
            }
        }
    }
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        if (attributeName.equals("SPRING_SECURITY_CONTEXT")) {
            if(event.getValue() instanceof SecurityContextImpl) {
                SecurityContextImpl securityContextImpl = (SecurityContextImpl) event.getValue();
                Authentication authentication =securityContextImpl.getAuthentication();
                String userId = authentication.getName();
                if(sessionMap.get(userId) != null) {
                    sessionMap.remove(userId);
                }
            }
        }
    }
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
    }
}