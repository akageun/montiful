package kr.geun.oss.montiful.routes.program.api;

import kr.geun.oss.montiful.app.program.dto.ProgramDTO;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.service.ProgramService;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.response.Res;
import kr.geun.oss.montiful.core.utils.SecUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Program Api
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/program")
public class ProgramV1Api {

	@Autowired
	private ProgramService programService;

	@Autowired
	private UrlService urlService;

	/**
	 * Single Data
	 *
	 * @param param
	 * @param result
	 * @return
	 */
	@GetMapping("/{programIdx}")
	public ResponseEntity<Res> get(@Valid ProgramDTO.GetReq param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		Optional<ProgramEntity> optionalProgramEntity = programService.get(param.getProgramIdx());
		if (optionalProgramEntity.isPresent() == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Res.of(false, "데이터가 없습니다."));
		}

		return ResponseEntity.ok(Res.of(true, "SUCCESS", optionalProgramEntity.get()));
	}

	/**
	 * Add Program
	 *
	 * @param param
	 * @param result
	 * @return
	 */
	@PostMapping(value = "")
	public ResponseEntity<Res> add(@Valid ProgramDTO.AddReq param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		Res res = programService.valid(param.getProgramName());
		if (res.getResult() == false) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

		String userId = SecUtils.userId();

		//@formatter:off
		ProgramEntity addParam = ProgramEntity.builder()
				.programName(param.getProgramName())
				.memo(param.getMemo())
				.createdUserId(userId)
				.updatedUserId(userId)
			.build();
		//@formatter:on

		ProgramEntity dbInfo = programService.add(addParam);

		return ResponseEntity.status(HttpStatus.CREATED).body(Res.of(true, "SUCCESS", dbInfo));
	}

	/**
	 * Modify Program
	 *
	 * @param param
	 * @param result
	 * @return
	 */
	@PutMapping(value = "")
	public ResponseEntity<Res> modify(@RequestBody @Valid ProgramDTO.ModifyReq param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		Optional<ProgramEntity> optionalProgramEntity = programService.get(param.getProgramIdx());
		if (optionalProgramEntity.isPresent() == false) {
			log.error("존재하지 않는 데이터 수정이 있습니다. 확인이 필요합니다. {} ", param.getProgramIdx());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Res.of(false, "데이터가 없습니다."));
		}

		Res res = programService.valid(param.getProgramName());
		if (res.getResult() == false) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

		String userId = SecUtils.userId();

		//@formatter:off
		ProgramEntity modifyParam = ProgramEntity.builder()
				.programIdx(param.getProgramIdx())
				.programName(param.getProgramName())
				.memo(param.getMemo())
				.updatedUserId(userId)
			.build();
		//@formatter:on

		ProgramEntity dbInfo = programService.modify(modifyParam);

		return ResponseEntity.status(HttpStatus.OK).body(Res.of(true, "SUCCESS", dbInfo));
	}

	/**
	 * Search API
	 *
	 * @param param
	 * @param result
	 * @return
	 */
	@GetMapping("/url/search")
	public ResponseEntity<Res> urlSearch(@Valid ProgramDTO.UrlSearch param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "파라미터 오류"));
		}

		List<UrlEntity> searchList = Optional.ofNullable(urlService.urlNameSearch(param.getKeyword())).orElseGet(Collections::emptyList);

		return ResponseEntity.ok(Res.of(true, "SUCCESS", searchList));
	}

	/**
	 * Search API
	 *
	 * @param param
	 * @param result
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<Res> search(@Valid ProgramDTO.Search param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "파라미터 오류"));
		}

		List<ProgramEntity> searchList = Optional.ofNullable(programService.search(param.getKeyword())).orElseGet(Collections::emptyList);

		return ResponseEntity.ok(Res.of(true, "SUCCESS", searchList));
	}
}
