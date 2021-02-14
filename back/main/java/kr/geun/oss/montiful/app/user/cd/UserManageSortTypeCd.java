package kr.geun.oss.montiful.app.user.cd;

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
public enum UserManageSortTypeCd implements ISortTypeCd {

    //@formatter:off
	U_ID("User Id", "userId"),
	C("Created At", "createdAt"),
	U("Updated At", "updatedAt"),
	EMAIL("Email", "email"),

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
    }}
