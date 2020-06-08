package br.com.sicredi.challenge.section.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sicredi.challenge.section.dao.VoteRepository;
import br.com.sicredi.challenge.section.entity.Associate;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.entity.Vote;
import br.com.sicredi.challenge.section.exception.BusinessException;

public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository repository;

	@Autowired
	private SessionService service;

	@Autowired
	private AssociateService associateService;

	@Override
	public Vote vote(Long idSession, String cpf, Boolean vote) {

		Session session = service.findSession(idSession);

		LocalDateTime now = LocalDateTime.now();
		if (checkSessionIsOpen(now, session)) {
			throw new BusinessException("${error.session.is.close}");
		}

		Associate associate = associateService.findAndSaveAssociate(cpf);
		if (hasAssociateVoted(associate, session)) {
			throw new BusinessException("${error.associate.already.voted}");
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
		return !now.isAfter(openTime) && !now.isBefore(closeTime);
	}

	private boolean hasAssociateVoted(Associate associate, Session session) {

		return session.getVotes().stream()
				.filter(vote -> vote.getAssociate().equals(associate))
				.findFirst()
				.isPresent();
	}

}
