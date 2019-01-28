package kr.geun.oss.montiful.core.pagination;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Pagination
 *
 * @author akageun
 */
@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE) //기본 생성자 사용못하도록 막음
public class PaginationInfo {

    public static PaginationInfo of(int pageNumber, int numberOfElements, long totalElements, int totalPageBlocks, int pageBlockSize) {
        PaginationInfo info = new PaginationInfo();
        info.pageNumber = pageNumber;
        info.numberOfElements = numberOfElements;
        info.totalElements = totalElements;
        info.totalPages = totalPageBlocks; //전체 페이지 블럭사이즈
        info.pageBlockSize = pageBlockSize;

        info.init();

        return info;
    }

    private int pageNumber; //현재 페이지
    private int numberOfElements; //현재 페이지에 나올 데이터 수
    private long totalElements;  //전체 데이터 수
    private int pageBlockSize; //페이지 블럭 수
    private int totalPages; //전체 페이지 수

    private int firstBlockPageNo; //첫번째 블럭 페이지 번호
    private int lastBlockPageNo; //마지막 블럭 페이지 번호

    private int firstPageNo = 1; //맨앞으로 갈 페이지 번호
    private int lastPageNo; //맨뒤로 갈 페이지 번호

    private int pageBlockNo; //페이지 블럭 번호

    private int preBlockPageNo; //이전 블럭 페이지번호
    private int nextBlockPageNo; //다음 블럭 페이지번호

    /**
     * 초기화
     */
    private void init() {
        this.pageBlockNo = pageNumber / pageBlockSize;
        if (pageNumber % pageBlockSize == 0) {
            this.pageBlockNo = pageBlockNo - 1;
        }

        if (pageNumber > pageBlockSize) {
            this.preBlockPageNo = pageNumber - (pageNumber % pageBlockSize);
            if (pageNumber % pageBlockSize == 0) {
                this.preBlockPageNo = pageNumber - pageBlockSize;
            }
        }

        this.firstBlockPageNo = pageBlockNo * pageBlockSize + 1;
        this.lastBlockPageNo = (pageBlockNo + 1) * pageBlockSize + 1;

        if (lastBlockPageNo > totalPages) {
            this.lastBlockPageNo = totalPages + 1;
        }

        this.nextBlockPageNo = (pageBlockNo + 1) * pageBlockSize;
        this.lastPageNo = totalPages;
    }
}
