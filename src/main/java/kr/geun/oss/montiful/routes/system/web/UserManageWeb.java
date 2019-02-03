package kr.geun.oss.montiful.routes.system.web;

import kr.geun.oss.montiful.app.user.dto.UserDTO;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.service.UserService;
import kr.geun.oss.montiful.core.pagination.PageRequestWrapper;
import kr.geun.oss.montiful.core.pagination.PaginationInfo;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Controller
@RequestMapping("/system")
public class UserManageWeb {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ModelAndView systemUserManage(@Valid UserDTO.PageReq param, BindingResult result) {
        if (result.hasErrors()) {
            return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
        }

        Page<UserEntity> rtnList = userService.page(PageRequestWrapper.of(param.getPageNumber(), 20, Sort.by(Sort.Direction.DESC, "createdAt")));

        ModelAndView mav = new ModelAndView();

        mav.addObject("paramInfo", param);
        mav.addObject("resultList", rtnList);
        mav.addObject("pagination",
            PaginationInfo.of(rtnList.getNumber(), rtnList.getNumberOfElements(), rtnList.getTotalElements(), rtnList.getTotalPages(), 3));

        mav.setViewName("system/userManage");
        return mav;
    }
}
