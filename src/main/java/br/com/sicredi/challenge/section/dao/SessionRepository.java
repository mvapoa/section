package br.com.sicredi.challenge.section.dao;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

	public Optional<Session> findByAgenda(Agenda agenda);

	public Set<Session> findAllByProcessedAndCloseTimeLessThanEqual(Boolean processed, LocalDateTime closeTime);

}
