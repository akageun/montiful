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
public enum ProgramManageSearchTypeCd {

	//@formatter:off
	CREATED_ID("Created User Id"),
	UPDATED_ID("Updated User Id"),
	PROGRAM_NAME("Program Name"),
	MEMO("Memo"),
	//@formatter:on
	;

	private String cdNm;

}
