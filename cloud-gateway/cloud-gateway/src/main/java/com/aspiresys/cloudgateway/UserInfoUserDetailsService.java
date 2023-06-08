package com.aspiresys.cloudgateway;

import com.aspiresys.cloudgateway.UserInfoUserDetails;
import com.aspiresys.cloudgateway.VO.Login;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Component
public class
UserInfoUserDetailsService implements ReactiveUserDetailsService {

    RestTemplate restTemplate =new RestTemplate();

    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
        Login customer = restTemplate.getForObject("http://localhost:9005/pharmacy-management/customer/login/" + username, Login.class);
        System.out.println(customer);
        if (customer != null) {
            return Mono.just(new UserInfoUserDetails(customer));
        }
        else {

            throw new UsernameNotFoundException("user not found" + username);
        }
    }
}

