package kr.geun.oss.montiful.routes.user.api;

import kr.geun.oss.montiful.app.user.dto.UserDTO;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.service.UserService;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

/**
 * User Api Controller
 *  - V1
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/user/api/v1")
public class UserV1Api {

	@Autowired
	private UserService userService;

	/**
	 * Login Api
	 *
	 * @param param
	 * @param result
	 * @param req
	 * @param res
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<Res> login(@Valid UserDTO.LoginReq param, BindingResult result, HttpServletRequest req, HttpServletResponse res) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		try {
			userService.login(param.getUserId(), param.getPassWd(), param.isRemember(), req, res);

			//TODO : 5회 이상 잘못된 로그인 시도시 잠금

		} catch (BadCredentialsException be) {
			log.debug("BadCredentialsException :param: {}, msg: {}, ex: {} ", param.toString(), be.getMessage(), be);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "유효하지 않은 계정정보입니다.\nID 또는 암호를 확인해주세요."));
		} catch (UsernameNotFoundException ue) {
			log.debug("BadCredentialsException :param: {}, msg: {}, ex: {} ", param.toString(), ue.getMessage(), ue);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "유효하지 않은 계정정보입니다.\nID 또는 암호를 확인해주세요."));
		} catch (DisabledException de) {
			log.debug("BadCredentialsException :param: {}, msg: {}, ex: {} ", param.toString(), de.getMessage(), de);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Res.of(false, "사용할 수 없는 계정 정보 입니다."));
		} catch (Exception e) {
			log.debug("BadCredentialsException :param: {}, msg: {}, ex: {} ", param.toString(), e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.of(false, "서버에러"));
		}

		return ResponseEntity.ok(Res.ok());
	}

	/**
	 * Sign Up
	 *
	 * @param param
	 * @param result
	 * @return
	 */
	@PostMapping("/signup")
	public ResponseEntity<Res> signUp(@Valid UserDTO.SingUpReq param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		Optional<UserEntity> optUserEntity = userService.get(param.getUserId());
		if (optUserEntity.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "이미 존재하는 아이디 입니다."));
		}

		UserEntity userEntity = UserEntity.builder().userId(param.getUserId()).passWd(param.getPassWd()).email(param.getEmail()).build();

		userService.add(userEntity);

		return ResponseEntity.ok(Res.ok());
	}

	/**
	 * Logout
	 *
	 * @param req
	 * @param res
	 * @return
	 */
	@PostMapping("/logout")
	public ResponseEntity<Res> logout(HttpServletRequest req, HttpServletResponse res) {
		userService.logout(req, res);

		return ResponseEntity.ok(Res.of(true, "SUCCESS"));
	}
}
