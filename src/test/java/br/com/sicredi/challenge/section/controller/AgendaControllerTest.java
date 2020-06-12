package br.com.sicredi.challenge.section.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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

import br.com.sicredi.challenge.section.dto.AgendaDto;
import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.service.AgendaService;

@EnableWebMvc
@SpringBootTest(classes = { AgendaController.class, ControllerHandler.class, ModelMapper.class })
public class AgendaControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private AgendaService service;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void shouldCreateAgenda() throws Exception {

		Long id = 1l;
		String title = "title";
		String description = "description";

		AgendaDto agendaDto = new AgendaDto(title, description);
		Agenda agenda = new Agenda(id, title, description);

		given(service.createAgenda(title, description)).willReturn(agenda);

		mockMvc.perform(post("/agenda/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(agendaDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.code", is(id.intValue())))
		.andExpect(jsonPath("$.title", is(title)))
		.andExpect(jsonPath("$.description", is(description)));
	}

	@Test
	public void shouldNotCreateAgendaWhenTitleIsEmpty() throws Exception {

		String title = "";
		String description = "description";

		AgendaDto agendaDto = new AgendaDto(title, description);

		mockMvc.perform(post("/agenda/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(agendaDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(1)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o título!")));
	}

	@Test
	public void shouldNotCreateAgendaWhenDescriptionIsEmpty() throws Exception {

		String title = "title";
		String description = "";

		AgendaDto agendaDto = new AgendaDto(title, description);

		mockMvc.perform(post("/agenda/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(agendaDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(1)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche a descrição!")));
	}

	@Test
	public void shouldNotCreateAgendaWhenParametersAreEmpties() throws Exception {

		String title = "";
		String description = "";

		AgendaDto agendaDto = new AgendaDto(title, description);

		mockMvc.perform(post("/agenda/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(agendaDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(2)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche a descrição!")))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o título!")));
	}


	@Test
	public void shouldNotCreateAgendaWhenParametersAreNull() throws Exception {

		String title = null;
		String description = null;

		AgendaDto agendaDto = new AgendaDto(title, description);

		mockMvc.perform(post("/agenda/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(agendaDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(2)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche a descrição!")))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o título!")));
	}

}
