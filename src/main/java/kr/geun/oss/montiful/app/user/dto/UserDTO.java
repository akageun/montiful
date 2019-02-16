package kr.geun.oss.montiful.app.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * User DTO
 *
 * @author akageun
 */
public class UserDTO {

	@Data
	@NoArgsConstructor
	public static class PageReq {

		@Min(0)
		private int pageNumber;
	}

	@Data
	@NoArgsConstructor
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
