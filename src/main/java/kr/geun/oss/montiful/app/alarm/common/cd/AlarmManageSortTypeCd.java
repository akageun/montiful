package kr.geun.oss.montiful.app.alarm.common.cd;

import kr.geun.oss.montiful.core.cd.ISortTypeCd;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum AlarmManageSortTypeCd implements ISortTypeCd {

    IDX("PK", "alarmIdx"),
    C("Created At", "createdAt"),
    U("Updated At", "updatedAt"),
    A_NM("Alarm Name", "alarmName"),
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
