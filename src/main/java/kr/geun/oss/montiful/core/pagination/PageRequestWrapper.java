package kr.geun.oss.montiful.core.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * PageRequest Wrapper Class
 *
 * @author akageun
 */
public class PageRequestWrapper {

	private static final int FIRST_PAGE_NUMBER = 0;
	private static final int MAX_ELEMENT_SIZE = 100; //TODO : 상수클래스로 이동해야함.
	private static final int DEFAULT_ELEMENT_SIZE = 20; //TODO : 상수클래스로 이동해야함.

	public static PageRequest of(int pageNumber, int elementSize, Sort sort) {
		if (pageNumber <= 0) {
			pageNumber = FIRST_PAGE_NUMBER;
		} else {
			pageNumber -= 1;
		}
		if (elementSize == 0 || elementSize > MAX_ELEMENT_SIZE) {
			elementSize = DEFAULT_ELEMENT_SIZE;

		}

		return PageRequest.of(pageNumber, elementSize, sort);
	}
}
