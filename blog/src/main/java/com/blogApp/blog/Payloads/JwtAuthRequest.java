package com.blogApp.blog.Payloads;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthRequest {

    private String email;
    private String password;
}
