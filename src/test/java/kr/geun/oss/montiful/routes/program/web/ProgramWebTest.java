package kr.geun.oss.montiful.routes.program.web;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.service.ProgramService;
import kr.geun.oss.montiful.core.pagination.PageRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(value = ProgramWeb.class)
public class ProgramWebTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProgramService programService;

    //    @Test
    //    public void programWeb404Test() throws Exception {
    //        //@formatter:off
//		mvc.perform(get("/program"))
//			.andExpect(status().isBadRequest())
//			;
//		//@formatter:on
    //    }

    @Test
    public void programWebTest() throws Exception {

        //@formatter:off
        Pageable pageable = PageRequestWrapper.of(0, 10, new Sort(Sort.Direction.DESC, "programIdx"));

        List<ProgramEntity> programEntityList = new ArrayList<>();
        programEntityList.add(ProgramEntity.builder().programName("Test").build());

        given(programService.page(pageable)).willReturn(new PageImpl<>(programEntityList, pageable , 1));

		mvc.perform(
		        get("/program")
                    .param("pageNumber", "0")
        )
			.andExpect(status().isOk())
			.andExpect(view().name("programManage"))
            .andExpect(model().attributeExists("resultList", "paramInfo", "pagination"))
			;
		//@formatter:on
    }

}
