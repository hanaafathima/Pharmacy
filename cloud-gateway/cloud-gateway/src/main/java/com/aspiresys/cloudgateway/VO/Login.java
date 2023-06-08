package com.aspiresys.cloudgateway.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    private Long id;
    private String email;
    private String password;
    private String role;
}
