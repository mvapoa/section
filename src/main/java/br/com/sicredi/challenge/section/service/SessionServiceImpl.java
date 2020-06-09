package br.com.sicredi.challenge.section.service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.challenge.section.dao.SessionRepository;
import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.entity.Session;

@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private SessionRepository repository;

	@Autowired
	private AgendaService service;

	@Override
	public Session openSession(Long idAgenda) {

		LocalDateTime closeTime = LocalDateTime.now().plusMinutes(1);
		return openSession(idAgenda, closeTime);
	}

	@Override
	public Session openSession(Long idAgenda, LocalDateTime closeTime) {

		Agenda agenda = service.findAgenda(idAgenda);

		if (hasSessionByAgenda(agenda)) {
			throw new InvalidParameterException("error.session.already.exist");
		}

		Session session = new Session();
		session.setAgenda(agenda);
		session.setOpenTime(LocalDateTime.now());
		session.setCloseTime(closeTime);
		session.setProcessed(Boolean.FALSE);
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
		if (!session.isPresent()) {
			throw new InvalidParameterException("error.session.not.found");
		}
		return session.get();
	}

	@Override
	public Set<Session> findAllSessionByProcessedAndCloseTime(Boolean processed, LocalDateTime closeTime) {

		return repository.findAllByProcessedAndCloseTimeLessThanEqual(processed, closeTime);
	}

	@Override
	public Session closeSession(Session session) {
		
		session.setProcessed(Boolean.TRUE);
		return repository.saveAndFlush(session);
	}

}
