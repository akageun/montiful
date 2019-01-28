package kr.geun.oss.montiful.routes.program.web;

import kr.geun.oss.montiful.app.program.dto.ProgramDTO;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.service.ProgramService;
import kr.geun.oss.montiful.core.pagination.PageRequestWrapper;
import kr.geun.oss.montiful.core.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Program Web Controller
 *
 * @author akageun
 */
@Slf4j
@Controller
public class ProgramWeb {

    @Autowired
    private ProgramService programService;

    /**
     * Program Page
     *
     * @param param
     * @param result
     * @return
     */
    @GetMapping(value = "/program")
    public ModelAndView getProgramPage(@Valid ProgramDTO.PageReq param, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.setViewName("/err/notFound");
            return modelAndView;
        }

        Page<ProgramEntity> rtnList = programService.page(
            PageRequestWrapper.of(param.getPageNumber(), 10, Sort.by(Sort.Direction.DESC, "programIdx")));

        modelAndView.addObject("paramInfo", param);
        modelAndView.addObject("resultList", rtnList);
        modelAndView.addObject("pagination",
            PaginationInfo.of(rtnList.getNumber(), rtnList.getNumberOfElements(), rtnList.getTotalElements(), rtnList.getTotalPages(), 5));

        modelAndView.setViewName("programManage");
        return modelAndView;
    }

    /**
     * Program Single View
     *
     * @param param
     * @param result
     * @return
     */
    @GetMapping(value = "/program/{programIdx}")
    public ModelAndView getProgramSingleView(@Valid ProgramDTO.GetReq param, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.setViewName("/err/notFound");
            return modelAndView;
        }

        Optional<ProgramEntity> optionalProgramEntity = programService.get(param.getProgramIdx());
        if (optionalProgramEntity.isPresent() == false) {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            modelAndView.setViewName("/err/notFound");
            return modelAndView;
        }

        modelAndView.addObject("result", optionalProgramEntity.get());

        //TODO : URL 관련 소스 추가해야함.

        modelAndView.setViewName("programSingle");

        return modelAndView;

    }
}
