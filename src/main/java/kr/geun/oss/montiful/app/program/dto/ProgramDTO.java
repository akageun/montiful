package kr.geun.oss.montiful.app.program.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Program DTO
 *
 * @author akageun
 */
public class ProgramDTO {

	@Data
	@NoArgsConstructor
	public static class PageReq {
		@Min(0)
		private int pageNumber;
	}

	@Data
	@NoArgsConstructor
	public static class GetReq {
		@Min(0)
		private Long programIdx;
	}

	@Data
	@NoArgsConstructor
	public static class AddReq {

		@NotBlank
		@Size(min = 1, max = 100)
		private String programName;
		private String memo;
	}

	@Data
	@NoArgsConstructor
	public static class ModifyReq {
		@Min(0)
		private Long programIdx;

		@NotBlank
		@Size(min = 1, max = 100)
		private String programName;
		private String memo;
	}

	@Data
	@NoArgsConstructor
	public static class Search {
		@NotBlank
		@Size(min = 1, max = 100)
		private String keyword;

	}
}
