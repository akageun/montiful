package kr.geun.oss.montiful.app.url.cd;

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
public enum UrlManageSearchTypeCd implements ISearchTypeCd {

	//@formatter:off
	IDX("PK", "urlIdx", Long.class),
	C("Created User Id","createdUserId", String.class),
	U("Updated User Id","updatedUserId", String.class),
	U_NM("Url Name","urlName", String.class),
	URL("Url","url", String.class),
	MM("Memo","memo", String.class),
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
