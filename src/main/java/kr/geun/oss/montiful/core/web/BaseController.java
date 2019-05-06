package kr.geun.oss.montiful.core.web;

import kr.geun.oss.montiful.core.cd.ISortTypeCd;
import kr.geun.oss.montiful.core.dto.CmnPageModule;
import kr.geun.oss.montiful.core.pagination.PageRequestWrapper;
import kr.geun.oss.montiful.core.pagination.PaginationInfo;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        mav.addObject("pagination",
                PaginationInfo.of(
                        rtnList.getNumber(),
                        rtnList.getNumberOfElements(),
                        rtnList.getTotalElements(),
                        rtnList.getTotalPages(),
                        pageBlockSize
                )
        );
    }

    /**
     * 공통 Pagination setting
     *
     * @param param
     * @param sortTypeCd
     * @param <B>
     * @return
     */
    protected <B extends CmnPageModule> Pageable setCmnPageable(B param, ISortTypeCd sortTypeCd) {

        Sort.Direction direction = CmnUtils.defaultEnumCode(Sort.Direction.class, StringUtils.upperCase(param.getSod()), Sort.Direction.DESC);

        param.setSot(sortTypeCd.getName());
        param.setSod(direction.name());

        Sort tmpSort = Sort.by(direction, sortTypeCd.getColumnName());
        Pageable pageRequest = PageRequestWrapper.of(param.getPn(), param.getEz(), tmpSort);

        param.setEz(pageRequest.getPageSize());

        return pageRequest;
    }

}
