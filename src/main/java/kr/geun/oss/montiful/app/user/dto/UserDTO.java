package kr.geun.oss.montiful.app.user.dto;

import kr.geun.oss.montiful.core.dto.CmnPageModule;
import lombok.*;

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

	@Getter
	@Builder
	@AllArgsConstructor
	public static class PageReq extends CmnPageModule {
		private String st; //SearchType
		private String sv; //SearchValue TODO : Create Search Custom Valid Annotation(Length...)
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
