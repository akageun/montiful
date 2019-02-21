package kr.geun.oss.montiful.routes.manage.url.web;

import kr.geun.oss.montiful.app.url.cd.StatusCheckTypeCd;
import kr.geun.oss.montiful.app.url.cd.UrlManageSearchTypeCd;
import kr.geun.oss.montiful.app.url.cd.UrlManageSortTypeCd;
import kr.geun.oss.montiful.app.url.dto.UrlDTO;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.service.UrlService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Url Manage Web Controller
 *
 * @author akageun
 */
@Slf4j
@Controller
@RequestMapping("/manage")
public class UrlManageWeb extends BaseController {

	@Autowired
	private UrlService urlService;

	/**
	 * URL Page
	 *
	 * @param param
	 * @param result
	 * @return
	 */
	@GetMapping("/url")
	public ModelAndView getUrlPage(@Valid UrlDTO.PageReq param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}
		UrlManageSortTypeCd sortTypeCd = CmnUtils.defaultEnumCodeStr(UrlManageSortTypeCd.class, param.getSot(), UrlManageSortTypeCd.IDX);

		Pageable pageable = setCmnPage(param, sortTypeCd);

		Page<UrlEntity> rtnList = urlService.page(pageable, param.getSt(), param.getSv());
		ModelAndView mav = new ModelAndView("manage/url/urlManage");

		mav.addObject("searchTypeCd", UrlManageSearchTypeCd.values());

		mav.addObject("sortTypeCd", UrlManageSortTypeCd.values());
		mav.addObject("sortDirectionCd", Sort.Direction.values());

		mav.addObject("paramInfo", param);
		mav.addObject("resultList", rtnList);
		setPage(mav, rtnList, Const.Page.DEFAULT_PAGE_BLOCK_SIZE);

		return mav;
	}

	@GetMapping("/url/form")
	public ModelAndView addUrlForm() {
		ModelAndView mav = new ModelAndView("manage/url/urlForm");

		mav.addObject("timeout", getTimeout());
		mav.addObject("statusCheckTypeCd", StatusCheckTypeCd.values());

		return mav;
	}

	@GetMapping("/url/form/{urlIdx}")
	public ModelAndView modifyUrlForm(@Valid UrlDTO.ModifyPage param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}

		ModelAndView mav = new ModelAndView("manage/url/urlForm");

		Optional<UrlEntity> optionalProgramEntity = urlService.get(param.getUrlIdx());
		if (optionalProgramEntity.isPresent() == false) {
			return CmnUtils.mav(HttpStatus.NOT_FOUND, "err/notFound");
		}

		mav.addObject("result", optionalProgramEntity.get());
		mav.addObject("timeout", getTimeout());
		mav.addObject("statusCheckTypeCd", StatusCheckTypeCd.values());

		return mav;
	}

	/**
	 * Generate Timeout
	 *
	 * @return
	 */
	private List<Integer> getTimeout() {
		return IntStream.rangeClosed(1, 100).mapToObj(i -> i * 100).collect(Collectors.toList());
	}
}
