package kr.geun.oss.montiful.app.program.cd;

import kr.geun.oss.montiful.core.cd.ISearchTypeCd;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프로그램 관리 검색타입 코드
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum ManageProgramSearchTypeCd implements ISearchTypeCd {

    IDX("PK", "programIdx", Long.class),
    C("Created User Id", "createdUserId", String.class),
    U("Updated User Id", "updatedUserId", String.class),
    P_NM("Program Name", "programName", String.class),
    MM("Memo", "memo", String.class),
    ;

    private String cdNm;
    private String column;
    private Class searchType;

    @Override
    public boolean isLongSearch() {
        return searchType == Long.class;

    }

    @Override
    public String getColumnName() {
        return this.column;
    }

}
