package br.com.sicredi.challenge.section.service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sicredi.challenge.section.dao.ResultRepository;
import br.com.sicredi.challenge.section.dto.ResultResponseDto;
import br.com.sicredi.challenge.section.entity.Result;
import br.com.sicredi.challenge.section.entity.Session;

@Service
public class ResultServiceImpl implements ResultService {

	@Value("${rabbitmq.queues.exchange}")
	private String rabbitExchange;

	@Value("${rabbitmq.queues.routingkey}")
	private String rabbitRoutineKey;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	private ResultRepository repository;

	@Autowired
	private SessionService service;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Result findResultByIdSession(Long idSession) {

		Session session = service.findSession(idSession);

		if (checkSessionIsOpen(session)) {
			throw new InvalidParameterException("error.session.is.open");
		}

		if (!checkSessionHasBeenProcessed(session)) {
			throw new InvalidParameterException("error.session.has.not.been.processed");
		}

		return findResult(session);
	}

	@Override
	public Result findResult(Session session) {

		Optional<Result> result = repository.findBySession(session);
		if (!result.isPresent()) {
			throw new InvalidParameterException("error.result.not.found");
		}
		return result.get();
	}

	@Override
	public void closeSessions() {

		Boolean processed = Boolean.FALSE;
		LocalDateTime closeTime = LocalDateTime.now();

		Set<Session> sessions = service.findAllSessionByProcessedAndCloseTime(processed, closeTime);
		for (Session session : sessions) {

			Result result = closeSession(session);
			ResultResponseDto response = modelMapper.map(result, ResultResponseDto.class);
			rabbitTemplate.convertAndSend(rabbitExchange, rabbitRoutineKey, response);
		}

	}

	@Override
	public Result closeSession(Session session) {

		Integer totalTrue = session.getVotes().stream()
				.filter(v -> v.getVote().equals(Boolean.TRUE))
				.collect(Collectors.toSet())
				.size();
		Integer totalFalse = session.getVotes().stream()
				.filter(v -> v.getVote().equals(Boolean.FALSE))
				.collect(Collectors.toSet())
				.size();
		Boolean winner = totalTrue >= totalFalse;

		Result result = new Result();
		result.setSession(service.closeSession(session));
		result.setWinner(winner);
		result.setTotalTrue(totalTrue);
		result.setTotalFalse(totalFalse);
		return repository.save(result);
	}

	private boolean checkSessionIsOpen(Session session) {

		LocalDateTime now = LocalDateTime.now();
		return now.isBefore(session.getCloseTime());
	}

	private boolean checkSessionHasBeenProcessed(Session session) {

		return session.getProcessed();
	}

}
