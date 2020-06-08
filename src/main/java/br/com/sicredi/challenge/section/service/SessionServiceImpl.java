package br.com.sicredi.challenge.section.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sicredi.challenge.section.dao.SessionRepository;
import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.entity.Session;
import br.com.sicredi.challenge.section.exception.BusinessException;

public class SessionServiceImpl implements SessionService {

	@Autowired
	private SessionRepository repository;

	@Autowired
	private AgendaService service;

	@Override
	public Session openSession(Long idAgenda) {

		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1l);
		return openSession(idAgenda, closeTime);
	}

	@Override
	public Session openSession(Long idAgenda, LocalDateTime closeTime) {

		Agenda agenda = service.findAgenda(idAgenda);

		if (hasSessionByAgenda(agenda)) {
			throw new BusinessException("${error.session.already.exist}");
		}

		Session session = new Session();
		session.setAgenda(agenda);
		session.setOpenTime(LocalDateTime.now());
		session.setCloseTime(closeTime);
		return repository.save(session);
	}

	@Override
	public boolean hasSessionByAgenda(Agenda agenda) {

		Optional<Session> session = repository.findByAgenda(agenda);
		return session.isPresent();
	}

	@Override
	public Session findSession(Long id) {

		Optional<Session> session = repository.findById(id);
		return Optional.of(session.get()).orElseThrow(() -> new BusinessException("${error.session.not.found}"));
	}

	@Override
	public Set<Session> findAllSessionByClosedAndCloseTime(Boolean closed, LocalDateTime closeTime) {

		return repository.findAllByClosedAndCloseTimeLessThanEqual(closed, closeTime);
	}

}
