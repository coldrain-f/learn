package me.coldrain.learn.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, RequestInfo> {

    @Override
    public RequestInfo buildDetails(HttpServletRequest request) {
        return RequestInfo.builder()
                .remoteAddress(request.getRemoteAddr())
                .sessionId(request.getSession().getId())
                .loginTime(LocalDateTime.now())
                .build();
    }
}
