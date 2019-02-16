package kr.geun.oss.montiful.app.program.dto;

import kr.geun.oss.montiful.app.program.cd.ProgramManageSearchTypeCd;
import kr.geun.oss.montiful.app.program.cd.ProgramManageSortTypeCd;
import kr.geun.oss.montiful.core.validation.annotation.EnumValid;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

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

		private ProgramManageSortTypeCd sortType;
		private Sort.Direction sortDirection;

		private ProgramManageSearchTypeCd searchType;
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

		private List<String> urlIdxs;
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

		private List<String> urlIdxs;
	}

	@Data
	@NoArgsConstructor
	public static class UrlSearch {
		@NotBlank
		@Size(min = 1, max = 100)
		private String keyword;

	}

	@Data
	@NoArgsConstructor
	public static class Search {
		@NotBlank
		@Size(min = 1, max = 100)
		private String keyword;

	}
}
