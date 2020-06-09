package br.com.sicredi.challenge.section.service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.challenge.section.client.UserInfoClient;
import br.com.sicredi.challenge.section.dao.VoteRepository;
import br.com.sicredi.challenge.section.dto.AssociateStatusDto;
import br.com.sicredi.challenge.section.dto.StatusDto;
import br.com.sicredi.challenge.section.entity.Associate;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.entity.Vote;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository repository;

	@Autowired
	private UserInfoClient client;

	@Autowired
	private SessionService service;

	@Autowired
	private AssociateService associateService;

	@Override
	public Vote vote(Long idSession, String cpf, Boolean vote) {

		Session session = service.findSession(idSession);

		LocalDateTime now = LocalDateTime.now();
		if (!checkSessionIsOpen(now, session)) {
			throw new InvalidParameterException("error.session.is.close");
		}

		AssociateStatusDto status = client.getAssociateStatus(cpf);
		if (StatusDto.UNABLE_TO_VOTE.equals(status.getStatus())) {
			throw new InvalidParameterException("error.associate.cannot.vote");
		}

		Associate associate = associateService.findAndSaveAssociate(cpf);
		if (hasAssociateVoted(associate, session)) {
			throw new InvalidParameterException("error.associate.already.voted");
		}

		Vote ballot = new Vote();
		ballot.setSession(session);
		ballot.setTimeVote(now);
		ballot.setAssociate(associate);
		ballot.setVote(vote);
		return repository.save(ballot);
	}

	private boolean checkSessionIsOpen(LocalDateTime now, Session session) {

		LocalDateTime openTime = session.getOpenTime();
		LocalDateTime closeTime = session.getCloseTime();
		Boolean processed = session.getProcessed();
		return now.isAfter(openTime) && now.isBefore(closeTime) && !processed.booleanValue();
	}

	private boolean hasAssociateVoted(Associate associate, Session session) {

		return session.getVotes().stream()
				.filter(vote -> vote.getAssociate().equals(associate))
				.findFirst()
				.isPresent();
	}

}
