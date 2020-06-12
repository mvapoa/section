package br.com.sicredi.challenge.section.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sicredi.challenge.section.dto.SessionDto;
import br.com.sicredi.challenge.section.dto.SessionResponseDto;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/session")
@Api(value = "Session")
public class SessionController {

	@Autowired
	public SessionService service;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Open a new session")
	@PostMapping("/open")
	public ResponseEntity<?> openSession(@Valid @RequestBody SessionDto sessionDto) {

		Long idAgenda = sessionDto.getIdAgenda();
		LocalDateTime closeTime = sessionDto.getCloseTime();

		Session session = closeTime == null ? service.openSession(idAgenda) : service.openSession(idAgenda, closeTime);

		SessionResponseDto response = modelMapper.map(session, SessionResponseDto.class);
		return ResponseEntity.ok(response);
	}

}
