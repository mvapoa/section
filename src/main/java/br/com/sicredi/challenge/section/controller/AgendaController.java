package br.com.sicredi.challenge.section.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sicredi.challenge.section.dto.AgendaDto;
import br.com.sicredi.challenge.section.dto.AgendaResponseDto;
import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.service.AgendaService;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

	@Autowired
	public AgendaService service;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/")
	public ResponseEntity<?> createAgenda(@Valid @RequestBody AgendaDto agendaDto) {

		Agenda agenda = service.createAgenda(agendaDto.getTitle(), agendaDto.getDescription());

		AgendaResponseDto response = modelMapper.map(agenda, AgendaResponseDto.class);
		return ResponseEntity.ok(response);
	}

}
