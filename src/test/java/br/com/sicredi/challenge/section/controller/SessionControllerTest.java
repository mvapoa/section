package br.com.sicredi.challenge.section.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.sicredi.challenge.section.dto.SessionDto;
import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.entity.Vote;
import br.com.sicredi.challenge.section.service.SessionService;

@EnableWebMvc
@SpringBootTest(classes = { SessionController.class, ControllerHandler.class, ModelMapper.class })
public class SessionControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private SessionService service;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void shouldOpenSessionWithCloseTime() throws Exception {

		Long idAgenda = 1l;
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1);

		String title = "title";
		String description = "description";

		Long idSession = 1l;
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		SessionDto sessionDto = new SessionDto(idAgenda, closeTime);
		Agenda agenda = new Agenda(idAgenda, title, description);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);
		
		given(service.openSession(idAgenda, closeTime)).willReturn(session);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		mockMvc.perform(post("/session/open")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(sessionDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.code", is(idSession.intValue())));
	}

	@Test
	public void shouldOpenSessionWithoutCloseTime() throws Exception {

		Long idAgenda = 1l;

		String title = "title";
		String description = "description";

		Long idSession = 1l;
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		SessionDto sessionDto = new SessionDto(idAgenda, null);
		Agenda agenda = new Agenda(idAgenda, title, description);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(service.openSession(idAgenda)).willReturn(session);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		mockMvc.perform(post("/session/open")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(sessionDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.code", is(idSession.intValue())));
	}

	@Test
	public void shouldNotOpenSessionWhenIdAgendaIsNull() throws Exception {

		Long idAgenda = null;
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1);

		String title = "title";
		String description = "description";

		Long idSession = 1l;
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		SessionDto sessionDto = new SessionDto(idAgenda, closeTime);
		Agenda agenda = new Agenda(idAgenda, title, description);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(service.openSession(idAgenda)).willReturn(session);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		mockMvc.perform(post("/session/open")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(sessionDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(1)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o código da pauta!")));
	}

	@Test
	public void shouldNotOpenSessionWhenCloseTimeLessThanNow() throws Exception {

		Long idAgenda = 1l;
		LocalDateTime closeTime = LocalDateTime.now().minusMinutes(1);

		String title = "title";
		String description = "description";

		Long idSession = 1l;
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		SessionDto sessionDto = new SessionDto(idAgenda, closeTime);
		Agenda agenda = new Agenda(idAgenda, title, description);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(service.openSession(idAgenda)).willReturn(session);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		mockMvc.perform(post("/session/open")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(sessionDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(1)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, a data e a hora deve ser maior que o horário atual.")));
	}

	@Test
	public void shouldNotOpenSessionWhenParametersAreNull() throws Exception {

		Long idAgenda = null;
		LocalDateTime closeTime = null;

		String title = "title";
		String description = "description";

		Long idSession = 1l;
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		SessionDto sessionDto = new SessionDto(idAgenda, closeTime);
		Agenda agenda = new Agenda(idAgenda, title, description);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(service.openSession(idAgenda)).willReturn(session);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		mockMvc.perform(post("/session/open")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(sessionDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(1)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o código da pauta!")));
	}

}
