package com.blogApp.blog.Payloads;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthResponse {

    private String token;
    private String email;
}
