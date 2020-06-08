package br.com.sicredi.challenge.section.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import br.com.sicredi.challenge.section.dao.ResultRepository;
import br.com.sicredi.challenge.section.entity.Result;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.exception.BusinessException;

public class ResultServiceImpl implements ResultService {

	@Value("${javainuse.rabbitmq.exchange}")
	private String exchange;

	@Value("${javainuse.rabbitmq.routingkey}")
	private String routingkey;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	private ResultRepository repository;

	@Autowired
	private SessionService service;

	public Result findResult(Long id) {

		Optional<Result> result = repository.findById(id);
		return Optional.of(result.get()).orElseThrow(() -> new BusinessException("${error.result.not.found}"));
	}

	public void closeSessions() {

		Boolean closed = Boolean.FALSE;
		LocalDateTime closeTime = LocalDateTime.now();

		Set<Session> sessions = service.findAllSessionByClosedAndCloseTime(closed, closeTime);
		for (Session session : sessions) {

			Result result = closeSession(session);
			rabbitTemplate.convertAndSend(exchange, routingkey, result);
		}

	}

	public Result closeSession(Session session) {

		Integer totalTrue = session.getVotes().stream().filter(v -> v.getVote().equals(Boolean.TRUE))
				.collect(Collectors.toSet()).size();
		Integer totalFalse = session.getVotes().stream().filter(v -> v.getVote().equals(Boolean.FALSE))
				.collect(Collectors.toSet()).size();
		Boolean winner = totalTrue >= totalFalse;

		Result result = new Result();
		result.setSession(session);
		result.setWinner(winner);
		result.setTotalTrue(totalTrue);
		result.setTotalFalse(totalFalse);
		return repository.save(result);
	}

}
