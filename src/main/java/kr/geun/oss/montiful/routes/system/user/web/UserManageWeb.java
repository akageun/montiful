package kr.geun.oss.montiful.routes.system.user.web;

import kr.geun.oss.montiful.app.user.cd.UserManageSearchTypeCd;
import kr.geun.oss.montiful.app.user.cd.UserManageSortTypeCd;
import kr.geun.oss.montiful.app.user.dto.UserDTO;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.service.UserService;
import kr.geun.oss.montiful.core.constants.Const;
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
 *
 *
 * @author akageun
 */
@Slf4j
@Controller
@RequestMapping("/system")
public class UserManageWeb extends BaseController {

	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public ModelAndView systemUserManage(@Valid UserDTO.PageReq param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}

		UserManageSortTypeCd sortTypeCd = CmnUtils.defaultEnumCodeStr(UserManageSortTypeCd.class, param.getSot(), UserManageSortTypeCd.C);
		Pageable pageable = setCmnPageable(param, sortTypeCd);

		Page<UserEntity> rtnList = userService.page(pageable, param.getSt(), param.getSv());

		ModelAndView mav = new ModelAndView("system/userManage");

		mav.addObject("searchTypeCd", UserManageSearchTypeCd.values());

		mav.addObject("sortTypeCd", UserManageSortTypeCd.values());
		mav.addObject("sortDirectionCd", Sort.Direction.values());

		mav.addObject("paramInfo", param);
		mav.addObject("resultList", rtnList);

		setPage(mav, rtnList, Const.Page.DEFAULT_PAGE_BLOCK_SIZE);

		return mav;
	}
}
