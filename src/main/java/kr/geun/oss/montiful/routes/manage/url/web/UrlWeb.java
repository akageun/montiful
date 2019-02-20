package kr.geun.oss.montiful.routes.manage.url.web;

import kr.geun.oss.montiful.app.url.cd.StatusCheckTypeCd;
import kr.geun.oss.montiful.app.url.dto.UrlDTO;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.service.UrlService;
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
public class UrlWeb {

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

		Page<UrlEntity> rtnList = urlService.page(PageRequestWrapper.of(param.getPageNumber(), 20, Sort.by(Sort.Direction.DESC, "urlIdx")));
		ModelAndView mav = new ModelAndView("manage/url/urlManage");

		mav.addObject("paramInfo", param);
		mav.addObject("resultList", rtnList);
		mav.addObject("pagination",
			PaginationInfo.of(rtnList.getNumber(), rtnList.getNumberOfElements(), rtnList.getTotalElements(), rtnList.getTotalPages(), 3));

		return mav;
	}

	@GetMapping("/url/form")
	public ModelAndView addUrlForm() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("timeout", getTimeout());
		mav.addObject("statusCheckTypeCd", StatusCheckTypeCd.values());

		mav.setViewName("manage/url/urlForm");
		return mav;
	}

	@GetMapping("/url/form/{urlIdx}")
	public ModelAndView modifyUrlForm(@Valid UrlDTO.ModifyPage param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}

		ModelAndView mav = new ModelAndView();

		Optional<UrlEntity> optionalProgramEntity = urlService.get(param.getUrlIdx());
		if (optionalProgramEntity.isPresent() == false) {
			return CmnUtils.mav(HttpStatus.NOT_FOUND, "err/notFound");
		}

		mav.addObject("result", optionalProgramEntity.get());
		mav.addObject("timeout", getTimeout());
		mav.addObject("statusCheckTypeCd", StatusCheckTypeCd.values());

		mav.setViewName("manage/url/urlForm");
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
