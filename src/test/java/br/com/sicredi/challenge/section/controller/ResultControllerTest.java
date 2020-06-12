package br.com.sicredi.challenge.section.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.sicredi.challenge.section.entity.Result;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.service.ResultService;

@EnableWebMvc
@SpringBootTest(classes = { ResultController.class, ControllerHandler.class, ModelMapper.class })
public class ResultControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private ResultService service;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void shouldOpenSessionWithCloseTime() throws Exception {

		Long idSession = 1l;

		Long id = 1l;
		Session session = new Session(idSession, null, null, null, null, null);
		Boolean winner = Boolean.TRUE;
		Integer totalTrue = new Integer(1);
		Integer totalFalse = new Integer(0);

		Result result = new Result(id, session, winner, totalTrue, totalFalse);

		given(service.findResultByIdSession(idSession)).willReturn(result);

		mockMvc.perform(get("/result/" + idSession))
		.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.code", is(result.getId().intValue())))
		.andExpect(jsonPath("$.codeSession", is(result.getSession().getId().intValue())))
		.andExpect(jsonPath("$.winner", is(result.getWinner())))
		.andExpect(jsonPath("$.totalTrue", is(result.getTotalTrue())))
		.andExpect(jsonPath("$.totalFalse", is(result.getTotalFalse())));
	}

}
