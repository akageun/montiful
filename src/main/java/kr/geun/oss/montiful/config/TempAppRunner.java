package kr.geun.oss.montiful.config;

import kr.geun.oss.montiful.app.alarm.common.repo.AlarmRepo;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.models.ProgramUrlEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramRepo;
import kr.geun.oss.montiful.app.program.repo.ProgramUrlRepo;
import kr.geun.oss.montiful.app.system.cd.SysConfCd;
import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import kr.geun.oss.montiful.app.url.cd.HealthStatusCd;
import kr.geun.oss.montiful.app.url.cd.StatusCheckTypeCd;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.repo.UrlAlarmRepo;
import kr.geun.oss.montiful.app.url.repo.UrlRepo;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.service.UserService;
import kr.geun.oss.montiful.core.constants.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author akageun
 */
@Slf4j
@Component
public class TempAppRunner implements CommandLineRunner {

    @Autowired
    private ProgramRepo programRepo;

    @Autowired
    private UrlRepo urlRepo;

    @Autowired
    private ProgramUrlRepo programUrlRepo;

    @Autowired
    private AlarmRepo alarmRepo;

    @Autowired
    private UrlAlarmRepo urlAlarmRepo;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Autowired
    private SysConfService sysConfService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        final String userId = Const.System.systemAdminUserId;
        final String urlPrefix = "http://localhost:%s";

        //@formatter:off
        ProgramEntity programParam = ProgramEntity.builder()
                .programName("Test Program Num 1")
                .createdUserId(userId)
                .updatedUserId(userId)
            .build();

        ProgramEntity rtnProgramInfo = programRepo.save(programParam);

        ProgramEntity programParam2 = ProgramEntity.builder()
                .programName("Test Program Num 2")
                .createdUserId(userId)
                .updatedUserId(userId)
            .build();

        ProgramEntity rtnProgramInfo2 = programRepo.save(programParam2);

        IntStream.range(3, 10).forEach(i -> programRepo.save(ProgramEntity.builder()
			.programName("Test Program Num " + i)
			.createdUserId(userId)
			.updatedUserId(userId)
			.build())
		);

        UrlEntity u1 = UrlEntity.builder()
                .url(String.format(urlPrefix, env.getRequiredProperty("server.port"))+"/sample/health/check/api")
				.urlName("테스트 11111222222221")
				.connectionTimeout(200)
				.readTimeout(500)
				.healthStatusCd(HealthStatusCd.HEALTH.name())
				.method(HttpMethod.GET.name())
				.statusCheckTypeCd(StatusCheckTypeCd.ONLY_200_CHECK.name())
				.createdUserId(userId)
				.updatedUserId(userId)
              .build();
        urlRepo.save(u1);

        UrlEntity u2 = UrlEntity.builder()
                .url(String.format(urlPrefix, env.getRequiredProperty("server.port"))+"/sample/health/check/api")
				.urlName("테스트5437345751")
				.connectionTimeout(200)
				.readTimeout(500)
				.healthStatusCd(HealthStatusCd.HEALTH.name())
				.method(HttpMethod.POST.name())
				.statusCheckTypeCd(StatusCheckTypeCd.ONLY_200_CHECK.name())
				.createdUserId(userId)
				.updatedUserId(userId)
              .build();
		urlRepo.save(u2);

        UrlEntity u3 =UrlEntity.builder()
                .url(String.format(urlPrefix, env.getRequiredProperty("server.port"))+"/sample/health/check/api2")
			.urlName("테스트 6342262346")
                    .connectionTimeout(200)
                    .readTimeout(500)
                    .healthStatusCd(HealthStatusCd.HEALTH.name())
                    .method(HttpMethod.GET.name())
                    .statusCheckTypeCd(StatusCheckTypeCd.SAME_STRING.name())
                    .statusCheckValue("OK")
                    .createdUserId(userId)
                    .updatedUserId(userId)
              .build();
        urlRepo.save(u3);

        UrlEntity u4 = UrlEntity.builder()
                .url(String.format(urlPrefix, env.getRequiredProperty("server.port"))+"/sample/health/check/api2")
			.urlName("테스트 23525235")
                    .connectionTimeout(200)
                    .readTimeout(500)
                    .healthStatusCd(HealthStatusCd.ERROR.name())
                    .method(HttpMethod.POST.name())
                    .statusCheckTypeCd(StatusCheckTypeCd.ONLY_200_CHECK.name())
                    .createdUserId(userId)
                    .updatedUserId(userId)
              .build();
        urlRepo.save(u4);

        UrlEntity u5 = UrlEntity.builder()
                .url(String.format(urlPrefix, env.getRequiredProperty("server.port"))+"/sample/health/check/api3")
			.urlName("테스트 ㅁㄴㄹㅇㅁㄴㅇㄻㄴ")
                    .connectionTimeout(200)
                    .readTimeout(500)
                    .healthStatusCd(HealthStatusCd.ERROR.name())
                    .method(HttpMethod.GET.name())
                    .statusCheckTypeCd(StatusCheckTypeCd.SUCCESS_2XX_CHECK.name())
                    .createdUserId(userId)
                    .updatedUserId(userId)
              .build();
		urlRepo.save(u5);

          UrlEntity u6 = UrlEntity.builder()
                .url(String.format(urlPrefix, env.getRequiredProperty("server.port"))+"/sample/health/check/api3")
			  	.urlName("테스트 11")
                    .connectionTimeout(200)
                    .readTimeout(500)
                    .healthStatusCd(HealthStatusCd.WARNING.name())
                    .method(HttpMethod.POST.name())
                    .statusCheckTypeCd(StatusCheckTypeCd.SAME_STRING.name())
                    .statusCheckValue("OK")
                    .createdUserId(userId)
                    .updatedUserId(userId)
              .build();
		urlRepo.save(u6);

		programUrlRepo.save(ProgramUrlEntity.builder().programIdx(rtnProgramInfo.getProgramIdx()).urlIdx(u2.getUrlIdx()).createdUserId(userId).build());
		programUrlRepo.save(ProgramUrlEntity.builder().programIdx(rtnProgramInfo.getProgramIdx()).urlIdx(u3.getUrlIdx()).createdUserId(userId).build());

		programUrlRepo.save(ProgramUrlEntity.builder().programIdx(rtnProgramInfo2.getProgramIdx()).urlIdx(u4.getUrlIdx()).createdUserId(userId).build());
		programUrlRepo.save(ProgramUrlEntity.builder().programIdx(rtnProgramInfo2.getProgramIdx()).urlIdx(u5.getUrlIdx()).createdUserId(userId).build());
		programUrlRepo.save(ProgramUrlEntity.builder().programIdx(rtnProgramInfo2.getProgramIdx()).urlIdx(u6.getUrlIdx()).createdUserId(userId).build());
		programUrlRepo.save(ProgramUrlEntity.builder().programIdx(rtnProgramInfo2.getProgramIdx()).urlIdx(u1.getUrlIdx()).createdUserId(userId).build());

        //@formatter:on
        userService.add(UserEntity.builder()
                .email("akageun@gmail.com")
                .passWd(passwordEncoder.encode("q1w2e3Q!"))
                .userId(Const.System.systemAdminUserId)
                .enable(true)
                .locked(false)
                .build()
        );


        initSystemConfig(); //TODO : 실서비스 때는 삭제해야함.
    }

    private void initSystemConfig() {
        for (SysConfCd value : SysConfCd.values()) {
            Optional<SysConfEntity> dbInfo = sysConfService.get(value.name());
            if (dbInfo.isPresent()) {
                continue;
            }
            //@formatter:off
			SysConfEntity sysConfEntity = SysConfEntity.builder()
				.confCd(value.name())
				.confValue(value.getDefaultValue())
				.createdUserId(Const.System.systemAdminUserId)
				.updatedUserId(Const.System.systemAdminUserId)
				.build();
			//@formatter:on

            sysConfService.add(sysConfEntity);
        }
    }

}
