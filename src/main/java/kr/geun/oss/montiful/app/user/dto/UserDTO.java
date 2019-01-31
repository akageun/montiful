package kr.geun.oss.montiful.app.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 *
 *
 * @author akageun
 */
public class UserDTO {

	@Data
	@NoArgsConstructor
	public static class SingUpReq {

		@NotBlank
		private String userId;

		@NotBlank
		@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})")
		private String passWd;

		@NotBlank
		private String email;
	}

	@Data
	@NoArgsConstructor
	public static class LoginReq {

		@NotBlank
		private String userId;

		@NotBlank
		private String passWd;

		private boolean remember;
	}
}
