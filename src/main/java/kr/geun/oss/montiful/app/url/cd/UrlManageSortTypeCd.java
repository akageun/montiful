package kr.geun.oss.montiful.app.url.cd;

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
public enum UrlManageSortTypeCd implements ISortTypeCd {

	//@formatter:off
	IDX("PK", "urlIdx"),
	C("Created At", "createdAt"),
	U("Updated At", "updatedAt"),
	U_NM("Url Name", "urlName"),
	H_ST("Health Status Code", "healthStatusCd"),

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
