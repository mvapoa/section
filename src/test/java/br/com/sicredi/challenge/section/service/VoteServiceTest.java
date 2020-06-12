package br.com.sicredi.challenge.section.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.sicredi.challenge.section.client.UserInfoClient;
import br.com.sicredi.challenge.section.dao.VoteRepository;
import br.com.sicredi.challenge.section.dto.AssociateStatusDto;
import br.com.sicredi.challenge.section.dto.StatusDto;
import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.entity.Associate;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.entity.Vote;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

	@InjectMocks
	private VoteServiceImpl service;

	@Mock
	private VoteRepository repository;

	@Mock
	private UserInfoClient client;

	@Mock
	private SessionService sessionService;

	@Mock
	private AssociateService associateService;

	@Test
	public void shouldAssociateVoteWhenSessionIsOpen() {

		Long idAssociate = 1l;
		String cpf = "99999999999";

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now().minusMinutes(1l);
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1l);
		Boolean processed = Boolean.FALSE;

		Boolean voteObj = Boolean.TRUE;
		LocalDateTime timeVote = LocalDateTime.now();

		Associate associate = new Associate(idAssociate, cpf);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);
		AssociateStatusDto associateStatus = new AssociateStatusDto(StatusDto.ABLE_TO_VOTE);

		Vote vote = new Vote(null, associate, session, voteObj, timeVote);

		given(sessionService.findSession(idSession)).willReturn(session);
		given(client.getAssociateStatus(cpf)).willReturn(associateStatus);
		given(associateService.findAndSaveAssociate(cpf)).willReturn(associate);
		given(repository.save(vote)).willAnswer(i -> i.getArgument(0));

		Vote response = service.vote(idSession, cpf, voteObj);

		assertEquals(response.getId(), vote.getId());
		assertEquals(response.getAssociate().toString(), vote.getAssociate().toString());
		assertEquals(response.getSession().toString(), vote.getSession().toString());
		assertEquals(response.getVote(), vote.getVote());
	}

	@Test
	public void shouldAssociateVoteWhenSessionIsOpenAndMoreVotes() {

		Long idAssociate = 1l;
		String cpf = "99999999999";
		Long idAssociateTwo = 2l;
		String cpfTwo = "88888888888";

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now().minusMinutes(1l);
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1l);
		Boolean processed = Boolean.FALSE;

		Long idVote = 1l;
		Boolean voteObj = Boolean.TRUE;
		LocalDateTime timeVote = LocalDateTime.now();

		Associate associate = new Associate(idAssociate, cpf);
		Associate associateTwo = new Associate(idAssociateTwo, cpfTwo);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);
		Vote voteTwo = new Vote(idVote, associateTwo, new Session(), voteObj, timeVote);
		votes.add(voteTwo);
		AssociateStatusDto associateStatus = new AssociateStatusDto(StatusDto.ABLE_TO_VOTE);

		Vote vote = new Vote(null, associate, session, voteObj, timeVote);

		given(sessionService.findSession(idSession)).willReturn(session);
		given(client.getAssociateStatus(cpf)).willReturn(associateStatus);
		given(associateService.findAndSaveAssociate(cpf)).willReturn(associate);
		given(repository.save(vote)).willAnswer(i -> i.getArgument(0));

		Vote response = service.vote(idSession, cpf, voteObj);

		assertEquals(response.getId(), vote.getId());
		assertEquals(response.getAssociate().toString(), vote.getAssociate().toString());
		assertEquals(response.getSession().toString(), vote.getSession().toString());
		assertEquals(response.getVote(), vote.getVote());
	}

	@Test
	public void shouldThrowExceptionWhenNotFoundSession() {

		Long idSession = 1l;
		String cpf = "99999999999";
		Boolean vote = Boolean.TRUE;

		given(sessionService.findSession(idSession))
				.willThrow(new InvalidParameterException("error.session.not.found"));

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.vote(idSession, cpf, vote);
		});
		assertTrue(thrown.getMessage().contains("error.session.not.found"));
	}

	@Test
	public void shouldThrowExceptionWhenSessionIsNotOpen() {

		Long idSession = 1l;
		String cpf = "99999999999";
		Boolean vote = Boolean.TRUE;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now().plusMinutes(1);
		LocalDateTime closeTime = LocalDateTime.now();
		Boolean processed = Boolean.FALSE;

		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(sessionService.findSession(idSession)).willReturn(session);

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.vote(idSession, cpf, vote);
		});
		assertTrue(thrown.getMessage().contains("error.session.is.close"));
	}

	@Test
	public void shouldThrowExceptionWhenSessionIsCloseButNotProcessed() {

		Long idSession = 1l;
		String cpf = "99999999999";
		Boolean vote = Boolean.TRUE;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now();
		LocalDateTime closeTime = LocalDateTime.now().minusMinutes(1l);
		Boolean processed = Boolean.FALSE;

		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(sessionService.findSession(idSession)).willReturn(session);

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.vote(idSession, cpf, vote);
		});
		assertTrue(thrown.getMessage().contains("error.session.is.close"));
	}

	@Test
	public void shouldThrowExceptionWhenSessionIsCloseButProcessed() {

		Long idSession = 1l;
		String cpf = "99999999999";
		Boolean vote = Boolean.TRUE;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now().minusMinutes(1l);
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1l);
		Boolean processed = Boolean.TRUE;

		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(sessionService.findSession(idSession)).willReturn(session);

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.vote(idSession, cpf, vote);
		});
		assertTrue(thrown.getMessage().contains("error.session.is.close"));
	}

	@Test
	public void shouldThrowExceptionWhenCpfIsInvalid() {

		Long idSession = 1l;
		String cpf = "99999999999";
		Boolean vote = Boolean.TRUE;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now().minusMinutes(1l);
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1l);
		Boolean processed = Boolean.FALSE;

		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		given(sessionService.findSession(idSession)).willReturn(session);
		given(client.getAssociateStatus(cpf)).willThrow(new InvalidParameterException("error.vote.cpf.invalid"));

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.vote(idSession, cpf, vote);
		});
		assertTrue(thrown.getMessage().contains("error.vote.cpf.invalid"));
	}

	@Test
	public void shouldThrowExceptionWhenAssociateCannotVote() {

		Long idSession = 1l;
		String cpf = "99999999999";
		Boolean vote = Boolean.TRUE;
		Agenda agenda = new Agenda();
		Set<Vote> votes = new HashSet<Vote>();
		LocalDateTime openTime = LocalDateTime.now().minusMinutes(1l);
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1l);
		Boolean processed = Boolean.FALSE;

		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);

		AssociateStatusDto associateStatus = new AssociateStatusDto(StatusDto.UNABLE_TO_VOTE);

		given(sessionService.findSession(idSession)).willReturn(session);
		given(client.getAssociateStatus(cpf)).willReturn(associateStatus);

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.vote(idSession, cpf, vote);
		});
		assertTrue(thrown.getMessage().contains("error.associate.cannot.vote"));
	}

	@Test
	public void shouldThrowExceptionWhenAssociateAlreadyVoted() {

		Long idAssociate = 1l;
		String cpf = "99999999999";

		Long idVote = 1l;
		Session sessionObj = new Session();
		Boolean vote = Boolean.TRUE;
		LocalDateTime timeVote = LocalDateTime.now();

		Long idSession = 1l;
		Agenda agenda = new Agenda();
		LocalDateTime openTime = LocalDateTime.now().minusMinutes(1l);
		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1l);
		Boolean processed = Boolean.FALSE;

		Associate associate = new Associate(idAssociate, cpf);
		Vote voteObj = new Vote(idVote, associate, sessionObj, vote, timeVote);
		Set<Vote> votes = new HashSet<Vote>();
		votes.add(voteObj);
		Session session = new Session(idSession, agenda, votes, openTime, closeTime, processed);
		AssociateStatusDto associateStatus = new AssociateStatusDto(StatusDto.ABLE_TO_VOTE);

		given(sessionService.findSession(idSession)).willReturn(session);
		given(client.getAssociateStatus(cpf)).willReturn(associateStatus);
		given(associateService.findAndSaveAssociate(cpf)).willReturn(associate);

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.vote(idSession, cpf, vote);
		});
		assertTrue(thrown.getMessage().contains("error.associate.already.voted"));
	}

}
