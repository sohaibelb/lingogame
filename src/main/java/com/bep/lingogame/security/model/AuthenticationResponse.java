package com.bep.lingogame.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse { //output
    private final String jwt;
}
