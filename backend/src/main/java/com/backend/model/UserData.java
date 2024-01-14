package com.backend.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserData {

    private String client_id;

    private String client_secret;

    private String grant_type;
}
