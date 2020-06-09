package br.com.sicredi.challenge.section.service;

import java.time.LocalDateTime;
import java.util.Set;

import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.entity.Session;

public interface SessionService {

	public Session openSession(Long idAgenda);

	public Session openSession(Long idAgenda, LocalDateTime closeTime);

	public boolean hasSessionByAgenda(Agenda agenda);

	public Session findSession(Long id);

	public Set<Session> findAllSessionByProcessedAndCloseTime(Boolean processed, LocalDateTime closeTime);

	public Session closeSession(Session session);

}
