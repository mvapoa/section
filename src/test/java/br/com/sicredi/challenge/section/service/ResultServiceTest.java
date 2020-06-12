package br.com.sicredi.challenge.section.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;

import br.com.sicredi.challenge.section.dao.ResultRepository;
import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.entity.Associate;
import br.com.sicredi.challenge.section.entity.Result;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.entity.Vote;

@ExtendWith(MockitoExtension.class)
public class ResultServiceTest {

	@InjectMocks
	private ResultServiceImpl service;

	@Mock
	private AmqpTemplate rabbitTemplate;

	@Mock
	private ResultRepository repository;

	@Mock
	private SessionService sessionService;

	@Mock
	private ModelMapper modelMapper;

	@Test
	public void shouldReturnResultBySession() {

		Long id = 1l;
		Session session = new Session();
		Boolean winner = Boolean.TRUE;
		Integer totalTrue = new Integer(1);
		Integer totalFalse = new Integer(0);

		Result result = new Result(id, session, winner, totalTrue, totalFalse);

		given(repository.findBySession(session)).willReturn(Optional.of(result));

		Result response = service.findResult(session);

		assertEquals(response.toString(), result.toString());
	}

	@Test
	public void shouldThrowExceptionWhenNotFoundResult() {

		Session session = new Session();

		given(repository.findBySession(session)).willReturn(Optional.empty());

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.findResult(session);
		});
		assertTrue(thrown.getMessage().contains("error.result.not.found"));
	}

	@Test
	public void shouldReturnResultByIdSession() {

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now().minusMinutes(1);
		Boolean processed = Boolean.TRUE;

		Long id = 1l;
		Boolean winner = Boolean.TRUE;
		Integer totalTrue = new Integer(1);
		Integer totalFalse = new Integer(0);

		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);
		Result result = new Result(id, session, winner, totalTrue, totalFalse);

		given(sessionService.findSession(idSession)).willReturn(session);
		given(repository.findBySession(session)).willReturn(Optional.of(result));

		Result response = service.findResultByIdSession(idSession);

		assertEquals(response.toString(), result.toString());
	}

	@Test
	public void shouldThrowExceptionWhenNotFoundSession() {

		Long idSession = 1l;

		given(sessionService.findSession(idSession))
				.willThrow(new InvalidParameterException("error.session.not.found"));

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.findResultByIdSession(idSession);
		});
		assertTrue(thrown.getMessage().contains("error.session.not.found"));
	}

	@Test
	public void shouldThrowExceptionWhenSessionIsOpen() {

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1);
		Boolean processed = Boolean.TRUE;

		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(sessionService.findSession(idSession)).willReturn(session);

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.findResultByIdSession(idSession);
		});
		assertTrue(thrown.getMessage().contains("error.session.is.open"));
	}

	@Test
	public void shouldThrowExceptionWhenSessionHasNotBeenProcessed() {

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now().minusMinutes(1);
		Boolean processed = Boolean.FALSE;

		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(sessionService.findSession(idSession)).willReturn(session);

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.findResultByIdSession(idSession);
		});
		assertTrue(thrown.getMessage().contains("error.session.has.not.been.processed"));
	}

	@Test
	public void shouldCalculateResultAndWinnerIsTrue() {

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		votes.add(new Vote(1l, new Associate(), new Session(), Boolean.TRUE, LocalDateTime.now()));
		votes.add(new Vote(2l, new Associate(), new Session(), Boolean.FALSE, LocalDateTime.now()));
		votes.add(new Vote(3l, new Associate(), new Session(), Boolean.TRUE, LocalDateTime.now()));
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now().minusMinutes(1);
		Boolean processedFalse = Boolean.FALSE;
		Boolean processedTrue = Boolean.TRUE;

		Session sessionInput = new Session(idSession, agenda, votes, openTime, closeTime, processedFalse);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processedTrue);

		Result result = new Result(null, session, Boolean.TRUE, new Integer(2), new Integer(1));

		given(sessionService.closeSession(sessionInput)).willReturn(session);
		given(repository.save(result)).willAnswer(i -> i.getArgument(0));

		Result response = service.closeSession(sessionInput);

		assertEquals(response.toString(), result.toString());
	}

	@Test
	public void shouldCalculateResultAndWinnerIsFalse() {

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		votes.add(new Vote(1l, new Associate(), new Session(), Boolean.FALSE, LocalDateTime.now()));
		votes.add(new Vote(2l, new Associate(), new Session(), Boolean.TRUE, LocalDateTime.now()));
		votes.add(new Vote(3l, new Associate(), new Session(), Boolean.FALSE, LocalDateTime.now()));
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now().minusMinutes(1);
		Boolean processedFalse = Boolean.FALSE;
		Boolean processedTrue = Boolean.TRUE;

		Session sessionInput = new Session(idSession, agenda, votes, openTime, closeTime, processedFalse);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processedTrue);

		Result result = new Result(null, session, Boolean.FALSE, new Integer(1), new Integer(2));

		given(sessionService.closeSession(sessionInput)).willReturn(session);
		given(repository.save(result)).willAnswer(i -> i.getArgument(0));

		Result response = service.closeSession(sessionInput);

		assertEquals(response.toString(), result.toString());
	}

	@Test
	public void shouldCalculateResultAndWinnerIsTrueWhenDraw() {

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		votes.add(new Vote(1l, new Associate(), new Session(), Boolean.FALSE, LocalDateTime.now()));
		votes.add(new Vote(2l, new Associate(), new Session(), Boolean.TRUE, LocalDateTime.now()));
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now().minusMinutes(1);
		Boolean processedFalse = Boolean.FALSE;
		Boolean processedTrue = Boolean.TRUE;

		Session sessionInput = new Session(idSession, agenda, votes, openTime, closeTime, processedFalse);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processedTrue);

		Result result = new Result(null, session, Boolean.TRUE, new Integer(1), new Integer(1));

		given(sessionService.closeSession(sessionInput)).willReturn(session);
		given(repository.save(result)).willAnswer(i -> i.getArgument(0));

		Result response = service.closeSession(sessionInput);

		assertEquals(response.toString(), result.toString());
	}

	@Test
	public void shouldCalculateResultAndWinnerIsTrueWhenWithoutVote() {

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now().minusMinutes(1);
		Boolean processedFalse = Boolean.FALSE;
		Boolean processedTrue = Boolean.TRUE;

		Session sessionInput = new Session(idSession, agenda, votes, openTime, closeTime, processedFalse);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processedTrue);

		Result result = new Result(null, session, Boolean.TRUE, new Integer(0), new Integer(0));

		given(sessionService.closeSession(sessionInput)).willReturn(session);
		given(repository.save(result)).willAnswer(i -> i.getArgument(0));

		Result response = service.closeSession(sessionInput);

		assertEquals(response.toString(), result.toString());
	}

}
