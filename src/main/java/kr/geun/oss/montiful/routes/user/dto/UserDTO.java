package kr.geun.oss.montiful.routes.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author akageun
 */
public class UserDTO {

    @Getter
    @AllArgsConstructor
    public static class SingUpReq {

        @NotBlank
        private String userId;

        @NotBlank
        @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})")
        private String passWd;

        @NotBlank
        @Email
        private String email;
    }

    @Getter
    @AllArgsConstructor
    public static class LoginReq {

        @NotBlank
        private String userId;

        @NotBlank
        private String passWd;

        private boolean remember;
    }

}
