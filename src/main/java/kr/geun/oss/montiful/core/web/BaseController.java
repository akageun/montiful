package kr.geun.oss.montiful.core.web;

import kr.geun.oss.montiful.core.pagination.PaginationInfo;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.ModelAndView;

/**
 * Base Controller 
 *
 * @author akageun
 */
public class BaseController {

	/**
	 * Setting Pagination
	 *
	 * @param mav
	 * @param rtnList
	 * @param pageBlockSize
	 */
	protected void setPage(ModelAndView mav, Page rtnList, int pageBlockSize) {
		//@formatter:off
		mav.addObject("pagination",
			PaginationInfo.of(
				rtnList.getNumber(),
				rtnList.getNumberOfElements(),
				rtnList.getTotalElements(),
				rtnList.getTotalPages(),
				pageBlockSize
			)
		);
		//@formatter:on
	}
}
