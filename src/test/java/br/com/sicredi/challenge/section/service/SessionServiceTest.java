package br.com.sicredi.challenge.section.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import br.com.sicredi.challenge.section.dao.SessionRepository;
import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.entity.Vote;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

	@InjectMocks
	private SessionServiceImpl service;

	@Mock
	private SessionRepository repository;

	@Mock
	private AgendaService agendaService;

	@Test
	public void shouldOpenSessionWithCloseTime() {

		Long idAgenda = 1l;
		String title = "title";
		String description = "description";

		Set<Vote> votes = null;
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		Agenda agenda = new Agenda(idAgenda, title, description);
		Session session = new Session(null, agenda, votes, openTime, closeTime, processed);

		given(agendaService.findAgenda(idAgenda)).willReturn(agenda);
		given(repository.findByAgenda(agenda)).willReturn(Optional.empty());
		given(repository.save(session)).willAnswer(i -> i.getArgument(0));

		Session response = service.openSession(idAgenda, closeTime);

		assertEquals(response.getId(), session.getId());
		assertEquals(response.getAgenda().toString(), session.getAgenda().toString());
		assertEquals(response.getVotes(), session.getVotes());
		assertEquals(response.getCloseTime(), session.getCloseTime());
		assertEquals(response.getProcessed(), session.getProcessed());
	}

	@Test
	public void shouldOpenSessionWithoutCloseTime() {

		Long idAgenda = 1l;
		String title = "title";
		String description = "description";

		Set<Vote> votes = null;
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1);
		Boolean processed = Boolean.FALSE;

		Agenda agenda = new Agenda(idAgenda, title, description);
		Session session = new Session(null, agenda, votes, openTime, closeTime, processed);

		given(agendaService.findAgenda(idAgenda)).willReturn(agenda);
		given(repository.findByAgenda(agenda)).willReturn(Optional.empty());
		given(repository.save(session)).willAnswer(i -> i.getArgument(0));

		Session response = service.openSession(idAgenda);

		assertEquals(response.getId(), session.getId());
		assertEquals(response.getAgenda().toString(), session.getAgenda().toString());
		assertEquals(response.getVotes(), session.getVotes());
		assertEquals(response.getProcessed(), session.getProcessed());
	}

	@Test
	public void shouldThrowExceptionWhenNotFoundAgenda() {

		Long idAgenda = 1l;
		LocalDateTime closeTime = LocalDateTime.now();

		given(agendaService.findAgenda(idAgenda)).willThrow(new InvalidParameterException("error.agenda.not.found"));

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.openSession(idAgenda, closeTime);
		});
		assertTrue(thrown.getMessage().contains("error.agenda.not.found"));
	}

	@Test
	public void shouldThrowExceptionWhenSessionAlreadyExists() {

		Long idAgenda = 1l;
		String title = "title";
		String description = "description";

		Long idSession = 1l;
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		Agenda agenda = new Agenda(idAgenda, title, description);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(agendaService.findAgenda(idAgenda)).willReturn(agenda);
		given(repository.findByAgenda(agenda)).willReturn(Optional.of(session));

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.openSession(idAgenda, closeTime);
		});
		assertTrue(thrown.getMessage().contains("error.session.already.exis"));
	}

	@Test
	public void shouldTrueWhenSessionHasAgenda() {

		Long idAgenda = 1l;
		String title = "title";
		String description = "description";

		Long idSession = 1l;
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		Agenda agenda = new Agenda(idAgenda, title, description);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(repository.findByAgenda(agenda)).willReturn(Optional.of(session));

		boolean response = service.hasSessionByAgenda(agenda);

		assertTrue(response);
	}

	@Test
	public void shouldFalseWhenSessionHasNotAgenda() {

		Long idAgenda = 1l;
		String title = "title";
		String description = "description";

		Agenda agenda = new Agenda(idAgenda, title, description);

		given(repository.findByAgenda(agenda)).willReturn(Optional.empty());

		boolean response = service.hasSessionByAgenda(agenda);

		assertFalse(response);
	}

	@Test
	public void shouldReturnSessionById() {

		Long id = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		Session session = new Session(id, agenda, votes, openTime, closeTime, processed);

		given(repository.findById(id)).willReturn(Optional.of(session));

		Session response = service.findSession(id);

		assertEquals(response.toString(), session.toString());
	}

	@Test
	public void shouldThrowExceptionWhenNotFoundSession() {

		Long id = 1l;

		given(repository.findById(id)).willReturn(Optional.empty());

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.findSession(id);
		});
		assertTrue(thrown.getMessage().contains("error.session.not.found"));
	}

	@Test
	public void shouldReturnSessionsByProcessedAndCloseTime() {

		Boolean processed = Boolean.TRUE;
		LocalDateTime closeTime = LocalDateTime.now();

		Set<Session> sessions = new HashSet<Session>();
		sessions.add(new Session());
		sessions.add(new Session());
		sessions.add(new Session());

		given(repository.findAllByProcessedAndCloseTimeLessThanEqual(processed, closeTime)).willReturn(sessions);

		Set<Session> response = service.findAllSessionByProcessedAndCloseTime(processed, closeTime);

		assertEquals(response.size(), sessions.size());
	}

	@Test
	public void shouldCloseSession() {

		Long id = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now();
		Boolean processedFalse = Boolean.FALSE;
		Boolean processedTrue = Boolean.TRUE;

		Session sessionInput = new Session(id, agenda, votes, openTime, closeTime, processedFalse);
		Session session = new Session(id, agenda, votes, openTime, closeTime, processedTrue);

		given(repository.saveAndFlush(sessionInput)).willAnswer(i -> i.getArgument(0));

		Session response = service.closeSession(sessionInput);

		assertEquals(response.toString(), session.toString());
	}

}
