package br.com.sicredi.challenge.section.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.challenge.section.entity.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}
