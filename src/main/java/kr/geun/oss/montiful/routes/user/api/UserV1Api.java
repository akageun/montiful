package kr.geun.oss.montiful.routes.user.api;

import kr.geun.oss.montiful.app.user.dto.UserDTO;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.service.UserService;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

/**
 *
 *
 * @author 김형근
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserV1Api {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<Res> login(@Valid UserDTO.LoginReq param, BindingResult result, HttpServletRequest req, HttpServletResponse res) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		try {
			userService.login(param.getUserId(), param.getPassWd(), param.isRemember(), req, res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(Res.of(true, "SUCCESS"));
	}

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

		userService.save(userEntity);

		return ResponseEntity.ok(Res.of(true, "SUCCESS"));
	}

	@GetMapping("/logout")
	public ResponseEntity<Res> logout(HttpServletRequest req, HttpServletResponse res) {
		userService.logout(req, res);

		return ResponseEntity.ok(Res.of(true, "SUCCESS"));
	}
}
