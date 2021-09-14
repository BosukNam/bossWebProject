package com.boss.common.login.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
public class LoginRestController {

    private static String authorizationRequestBaseUri = "oauth2/authorization";

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/login/urls")
    public Map oauth2AuthenticationUrls() {
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
        Iterable<ClientRegistration> clientRegistrations = null;

        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);

        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));

        return oauth2AuthenticationUrls;
    }
    
    @GetMapping("/oauthUser")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
    @GetMapping("/user")
    public Map<String, Object> user(HttpSession session) {

        Object securityContextObject = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if(securityContextObject != null) {
            SecurityContext securityContext = (SecurityContext)securityContextObject;
        }


        Map<String, Object> sessionAttrs = new HashMap<>();
        Iterator<String> attrs = session.getAttributeNames().asIterator();
        while(attrs.hasNext()) {
            String attr = attrs.next();
            sessionAttrs.put(attr, session.getAttribute(attr));
        }
        return sessionAttrs;
    }
}
