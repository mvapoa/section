package br.com.sicredi.challenge.section.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sicredi.challenge.section.dto.VoteDto;
import br.com.sicredi.challenge.section.dto.VoteResponseDto;
import br.com.sicredi.challenge.section.entity.Vote;
import br.com.sicredi.challenge.section.service.VoteService;

@Controller
@RequestMapping("/vote")
public class VoteController {

	@Autowired
	private VoteService service;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/")
	public ResponseEntity<?> vote(@Valid @RequestBody VoteDto voteDto) {

		Vote vote = service.vote(voteDto.getIdSession(), voteDto.getCpf(), voteDto.getVote());

		VoteResponseDto response = modelMapper.map(vote, VoteResponseDto.class);
		return ResponseEntity.ok(response);
	}

}
