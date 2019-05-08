package kr.geun.oss.montiful.routes.manage.program.api;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.service.ProgramService;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.cd.ErrorCd;
import kr.geun.oss.montiful.core.exception.BaseException;
import kr.geun.oss.montiful.core.response.Res;
import kr.geun.oss.montiful.core.utils.SecUtils;
import kr.geun.oss.montiful.routes.manage.program.dto.ManageProgramDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Program Manage Api
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/manage/program/api/v1")
public class ManageProgramV1Api {

    @Autowired
    private ProgramService programService;

    @Autowired
    private UrlService urlService;

    /**
     * 단건조회
     *
     * @param programIdx
     * @return
     */
    @GetMapping("/{programIdx}")
    public ResponseEntity<Res> get(
            @PathVariable Long programIdx
    ) {

        if (programIdx == null) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        Optional<ProgramEntity> optionalProgramEntity = programService.get(programIdx);
        if (optionalProgramEntity.isPresent() == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Res.of(false, "데이터가 없습니다."));
        }

        List<UrlEntity> urlList = urlService.urlProgramList(programIdx);

        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("result", optionalProgramEntity.get());
        rtnMap.put("urlList", urlList);

        return ResponseEntity.ok(Res.of(true, "SUCCESS", rtnMap));
    }

    /**
     * Add Program
     *
     * @param param
     * @param result
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity<Res> add(
            @Valid ManageProgramDTO.AddReq param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        programService.valid(param.getProgramName(), null);

        String userId = SecUtils.userId();

        ProgramEntity addParam = ProgramEntity.builder()
                .programName(param.getProgramName())
                .memo(param.getMemo())
                .createdUserId(userId)
                .updatedUserId(userId)
                .build();

        programService.add(addParam, param.getUrlIdxs());

        return ResponseEntity.status(HttpStatus.CREATED).body(Res.of(true, "SUCCESS"));
    }

    /**
     * Modify Program
     *
     * @param param
     * @param result
     * @return
     */
    @PutMapping(value = "")
    public ResponseEntity<Res> modify(
            @RequestBody @Valid ManageProgramDTO.ModifyReq param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        Optional<ProgramEntity> optionalProgramEntity = programService.get(param.getProgramIdx());
        if (optionalProgramEntity.isPresent() == false) {
            log.error("존재하지 않는 데이터 수정이 있습니다. 확인이 필요합니다. {} ", param.getProgramIdx());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Res.of(false, "데이터가 없습니다."));
        }

        programService.valid(param.getProgramName(), param.getProgramIdx());

        String userId = SecUtils.userId();

        ProgramEntity modifyParam = ProgramEntity.builder()
                .programIdx(param.getProgramIdx())
                .programName(param.getProgramName())
                .memo(param.getMemo())
                .updatedUserId(userId)
                .build();

        programService.modify(modifyParam, param.getUrlIdxs());

        return ResponseEntity.status(HttpStatus.OK).body(Res.of(true, "SUCCESS"));
    }

    /**
     * Search API
     *
     * @param param
     * @param result
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<Res> search(
            @Valid ManageProgramDTO.Search param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        List<ProgramEntity> searchList = programService.search(param.getKeyword());

        return ResponseEntity.ok(Res.of(true, "SUCCESS", searchList));
    }

    /**
     * URL Search API
     *
     * @param param
     * @param result
     * @return
     */
    @GetMapping("/url/search")
    public ResponseEntity<Res> urlSearch(
            @Valid ManageProgramDTO.UrlSearch param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        List<UrlEntity> searchList = urlService.urlNameSearch(param.getKeyword());

        return ResponseEntity.ok(Res.of(true, "SUCCESS", searchList));
    }

}
