package kr.geun.oss.montiful.app.program.cd;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum ProgramManageSortTypeCd {

	//@formatter:off
	IDX("PK", "programIdx"),
	CREATED_AT("Created At", "createdAt"),
	UPDATED_AT("Updated At", "updatedAt"),
	PROGRAM_NAME("Program Name", "programName"),

	//@formatter:on
	;

	private String cdNm;
	private String columnName;

}
