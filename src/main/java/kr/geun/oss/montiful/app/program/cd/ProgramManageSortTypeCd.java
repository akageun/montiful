package kr.geun.oss.montiful.app.program.cd;

import kr.geun.oss.montiful.core.cd.ISortTypeCd;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum ProgramManageSortTypeCd implements ISortTypeCd {

	//@formatter:off
	IDX("PK", "programIdx"),
	C("Created At", "createdAt"),
	U("Updated At", "updatedAt"),
	P_NM("Program Name", "programName"),

	//@formatter:on
	;

	private String cdNm;
	private String columnName;

	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public String getColumnName() {
		return this.columnName;
	}

}
