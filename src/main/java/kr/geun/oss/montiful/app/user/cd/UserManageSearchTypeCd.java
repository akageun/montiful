package kr.geun.oss.montiful.app.user.cd;

import kr.geun.oss.montiful.core.cd.ISearchTypeCd;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum UserManageSearchTypeCd implements ISearchTypeCd {

	//@formatter:off
	U_ID("User Id", "userId", String.class),
	EMAIL("Email", "email", String.class),
	//@formatter:on
	;

	private String cdNm;
	private String column;
	private Class searchType;

	@Override
	public boolean isLongSearch() {
		if (searchType == Long.class) {
			return true;
		}

		return false;
	}

	@Override
	public String getColumnName() {
		return this.column;
	}

}
