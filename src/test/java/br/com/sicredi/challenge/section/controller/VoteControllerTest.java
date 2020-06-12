package br.com.sicredi.challenge.section.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.time.LocalDateTime;

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

import br.com.sicredi.challenge.section.dto.VoteDto;
import br.com.sicredi.challenge.section.entity.Associate;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.entity.Vote;
import br.com.sicredi.challenge.section.service.VoteService;

@EnableWebMvc
@SpringBootTest(classes = { VoteController.class, ControllerHandler.class, ModelMapper.class })
public class VoteControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private VoteService service;

	@BeforeEach
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void shouldVote() throws Exception {

		Long idSession = 1l;
		String cpf = "92631095081";
		Boolean vote = Boolean.TRUE;

		Long idVote = 1l;
		Associate associate = new Associate();
		Session session = new Session();
		LocalDateTime timeVote = LocalDateTime.now();

		VoteDto voteDto = new VoteDto(idSession, cpf, vote);
		Vote voteObj = new Vote(idVote, associate, session, vote, timeVote);

		given(service.vote(voteDto.getIdSession(), voteDto.getCpf(), voteDto.getVote())).willReturn(voteObj);

		mockMvc.perform(post("/vote/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(voteDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.code", is(idVote.intValue())))
		.andExpect(jsonPath("$.vote", is(vote)));
	}

	@Test
	public void shouldNotVoteWhenIdSessionIsNull() throws Exception {

		Long idSession = null;
		String cpf = "92631095081";
		Boolean vote = Boolean.TRUE;

		VoteDto voteDto = new VoteDto(idSession, cpf, vote);

		mockMvc.perform(post("/vote/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(voteDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(1)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o código da sessão de votação!")));
	}

	@Test
	public void shouldNotVoteWhenCpfIsNull() throws Exception {

		Long idSession = 1l;
		String cpf = null;
		Boolean vote = Boolean.TRUE;

		VoteDto voteDto = new VoteDto(idSession, cpf, vote);

		mockMvc.perform(post("/vote/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(voteDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(1)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o CPF!")));
	}

	@Test
	public void shouldNotVoteWhenCpfIsEmpty() throws Exception {

		Long idSession = 1l;
		String cpf = "";
		Boolean vote = Boolean.TRUE;

		VoteDto voteDto = new VoteDto(idSession, cpf, vote);

		mockMvc.perform(post("/vote/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(voteDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(2)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o CPF!")))
		.andExpect(jsonPath("$.errors", hasItem("O CPF está inválido.")));
	}

	@Test
	public void shouldNotVoteWhenCpfIsInvalid() throws Exception {

		Long idSession = 1l;
		String cpf = "99999999999";
		Boolean vote = Boolean.TRUE;

		VoteDto voteDto = new VoteDto(idSession, cpf, vote);

		mockMvc.perform(post("/vote/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(voteDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(1)))
		.andExpect(jsonPath("$.errors", hasItem("O CPF está inválido.")));
	}

	@Test
	public void shouldNotVoteWhenVoteIsNull() throws Exception {

		Long idSession = 1l;
		String cpf = "92631095081";
		Boolean vote = null;

		VoteDto voteDto = new VoteDto(idSession, cpf, vote);

		mockMvc.perform(post("/vote/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(voteDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(1)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, informe a sua votação!")));
	}

	@Test
	public void shouldNotVoteWhenParametersAreNull() throws Exception {

		Long idSession = null;
		String cpf = null;
		Boolean vote = null;

		VoteDto voteDto = new VoteDto(idSession, cpf, vote);

		mockMvc.perform(post("/vote/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(voteDto)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.length()", is(3)))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o código da sessão de votação!")))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, preenche o CPF!")))
		.andExpect(jsonPath("$.errors", hasItem("Por favor, informe a sua votação!")));
	}

}
