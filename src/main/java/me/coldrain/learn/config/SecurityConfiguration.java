package me.coldrain.learn.config;

import lombok.RequiredArgsConstructor;
import me.coldrain.learn.security.CustomAuthenticationDetailsSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 설정시 @PreAuthorize 사용 가능
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationDetailsSource customAuthenticationDetailsSource;

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        // 이렇게 설정해 놓으면 ADMIN 권한은 USER 권한을 모두 접근 할 수 있다.
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // withDefaultPasswordEncoder() 는 안전하진 않지만 테스트에 한정해서는 사용해도 된다.
        // 로그인이 되지 않는 경우는 csrf 를 확인해 보자.
        auth
                .inMemoryAuthentication()
                .withUser(
                        User.withDefaultPasswordEncoder()
                                .username("olaf@naver.com")
                                .password("olaf")
                                .roles("USER")
                ).withUser(
                        User.withDefaultPasswordEncoder()
                                .username("elsa@naver.com")
                                .password("elsa")
                                .roles("ADMIN")
                );
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 스프링 시큐리티 필터가 적용되지 않도록 설정한다.
        web.ignoring()
                .antMatchers("/h2-console/**")
                .antMatchers("/assets/**")
                // 스프링 부트가 제공하는 static 리소스들의 기본위치를 다 가져와서 스프링 시큐리티에서 제외
                //.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                /**
                 * formLogin() 적용시 GET /login 을 요청하면 기본 시큐리티 페이지가 표시된다.
                 * -> permitAll() 필요X
                 * UsernamePasswordAuthenticationFilter
                 * , DefaultLoginPageGeneratingFilter
                 * , DefaultLogoutPageGeneratingFilter 들이 추가된다.
                 */
                .formLogin()
                // loginPage()로 기본 시큐리티 페이지가 아닌 사용자 정의 로그인 페이지를 띄워줄 수 있다.
                // -> permitAll() 필요
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login")
                .authenticationDetailsSource(customAuthenticationDetailsSource)
                // alwaysUse = true:
                // 로그인하지 않은 상태로 요청시 /home -> /login -> defaultSuccessUrl 으로 이동한다.
                // alwaysUser = false:
                // 로그인하지 않은 상태로요청시 /home -> /login -> /home 으로 이동한다.
                .defaultSuccessUrl("/home", false)
                .and()
                .logout().logoutSuccessUrl("/login")
                .and()
//                .exceptionHandling().accessDeniedPage() // 권한이 없는데 접근시 보여줄 페이지 설정
        ;

    }
}