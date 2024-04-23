package br.com.firstdecisionapp.firstdecisionapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDataOAuth2ForTokenJwt {

    private String email;
    private String userName;
    private String userId;

}
