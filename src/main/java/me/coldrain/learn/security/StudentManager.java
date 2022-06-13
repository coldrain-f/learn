package me.coldrain.learn.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

// 학생의 통행증을 발급해 주는 객체
@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {

    // 학생의 리스트를 가지고 있어야한다. ( 실제로는 DB로 구현 )
    // 여기서는 테스트로 컬렉션으로 설정
    private HashMap<String, Student> studentDB = new HashMap<>();


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token
                = (UsernamePasswordAuthenticationToken) authentication;
        if (studentDB.containsKey(token.getName())) {
            Student student = studentDB.get(token.getName());
            return StudentAuthenticationToken.builder()
                    .principal(student)
                    .authenticated(true)
                    .build();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // UsernamePasswordAuthenticationFilter를 통해서 토큰을 발급받기 때문에
        // UsernamePasswordAuthenticationToken 을 지원하도록 설정한다.
        return authentication == UsernamePasswordAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Student("hong", "홍길동", Set.of(new SimpleGrantedAuthority("ROLE_USER"))),
                new Student("kang", "강아지", Set.of(new SimpleGrantedAuthority("ROLE_USER"))),
                new Student("rang", "호랑이", Set.of(new SimpleGrantedAuthority("ROLE_USER")))
        ).forEach(s -> studentDB.put(s.getId(), s));
    }
}
