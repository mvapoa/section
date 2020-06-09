package br.com.sicredi.challenge.section.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sicredi.challenge.section.dto.ResultResponseDto;
import br.com.sicredi.challenge.section.entity.Result;
import br.com.sicredi.challenge.section.service.ResultService;

@Controller
@RequestMapping("/result")
public class ResultController {

	@Autowired
	public ResultService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/{idSession}")
	public ResponseEntity<?> getResult(@PathVariable("idSession") Long idSession) {

		Result result = service.findResultByIdSession(idSession);

		ResultResponseDto response = modelMapper.map(result, ResultResponseDto.class);
		return ResponseEntity.ok(response);
	}

	@Scheduled(cron = "${schedule.task.close}")
	public void scheduleTaskUsingCronExpression() {

		service.closeSessions();
	}

}
