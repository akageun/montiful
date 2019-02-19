package kr.geun.oss.montiful.app.program.dto;

import lombok.*;

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

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PageReq {
		private int pageNumber;
		private int elementSize;

		private String searchType; //ProgramManageSearchTypeCd
		private String sortType; //ProgramManageSortTypeCd
		private String sortDirection; //Sort.Direction

		public void setSortType(String sortType) {
			this.sortType = sortType;
		}

		public void setSortDirection(String sortDirection) {
			this.sortDirection = sortDirection;
		}
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
