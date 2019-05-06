package kr.geun.oss.montiful.app.program.cd;

import kr.geun.oss.montiful.core.cd.ISortTypeCd;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프로그램 관리 정렬 타입 코드
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum ManageProgramSortTypeCd implements ISortTypeCd {

    IDX("PK", "programIdx"),
    C("Created At", "createdAt"),
    U("Updated At", "updatedAt"),
    P_NM("Program Name", "programName"),
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
