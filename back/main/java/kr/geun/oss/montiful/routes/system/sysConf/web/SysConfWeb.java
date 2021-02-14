package kr.geun.oss.montiful.routes.system.sysConf.web;

import kr.geun.oss.montiful.app.system.dto.SysConfDTO;
import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import kr.geun.oss.montiful.core.constants.Const;
import kr.geun.oss.montiful.core.pagination.PageRequestWrapper;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import kr.geun.oss.montiful.core.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * System Configuration Web
 *
 * @author akageun
 */
@Slf4j
@Controller
@RequestMapping("/system")
public class SysConfWeb extends BaseController {

    @Autowired
    private SysConfService sysConfService;

    /**
     * System Config 페이지
     *
     * @param param
     * @param result
     * @return
     */
    @GetMapping("/configuration")
    public ModelAndView systemConfigPage(
            @Valid SysConfDTO.PageReq param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
        }

        Pageable pageable = PageRequestWrapper.of(param.getPn(), 0, Sort.by(Sort.Direction.DESC, "confCd"));
        Page<SysConfEntity> rtnList = sysConfService.page(pageable);

        ModelAndView mav = new ModelAndView("system/configuration");

        mav.addObject("resultList", rtnList);
        mav.addObject("paramInfo", param);
        setPage(mav, rtnList, Const.Page.DEFAULT_PAGE_BLOCK_SIZE);

        return mav;
    }
}
