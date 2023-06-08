package com.aspiresys.cloudgateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity.csrf().disable()
                .authenticationManager(authenticationManager())
                .authorizeExchange(it -> it
                        .pathMatchers("/pharmacy-management/customer/new",
                                "/pharmacy-management/customer/{id}",
                                "/pharmacy-management/customer/list",
                                "/pharmacy-management/customer/search",
                                "/pharmacy-management/customer/remove/{id}",
                                "/pharmacy-management/product/**",
                                "/pharmacy-management/order/search",
                                "/pharmacy-management/order/remove/{id}")
                        .hasRole("ADMIN")
                        .pathMatchers("/pharmacy-management/customer/change/{id}",
                                "/pharmacy-management/product/list",
                                "/pharmacy-management/product/{id}",
                                "/pharmacy-management/product/search",
                                "/pharmacy-management/order/**")
                        .hasRole("USER")
                )
                .httpBasic()
                .and().build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(new UserInfoUserDetailsService());
        manager.setPasswordEncoder(getPasswordEncoder());
        return manager;
    }
}
