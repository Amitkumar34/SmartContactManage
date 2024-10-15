package com.project.scm.SmartContactManager.config;

import com.project.scm.SmartContactManager.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("test")
//                .passwordEncoder(new Function<String, String>() {
//                    @Override
//                    public String apply(String s) {
//                        return s;
//                    }
//                })
//                .password("test").roles("USER", "ADMIN").build();
//        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
//        return inMemoryUserDetailsManager;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
//        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.formLogin(formLoginConfigurer -> {
            formLoginConfigurer
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .successForwardUrl("/user/dashboard")
//                    .failureForwardUrl("/login?error=true")
                    .usernameParameter("username")
                    .passwordParameter("password");

        });
//        httpSecurity.csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
//
//            }
//        });
//        httpSecurity.logout(logoutConfigurer -> logoutConfigurer.logoutUrl("/logout"));
        return httpSecurity.build();
    }

}
