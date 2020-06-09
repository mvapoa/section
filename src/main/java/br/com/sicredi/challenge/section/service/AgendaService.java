package br.com.sicredi.challenge.section.service;

import br.com.sicredi.challenge.section.entity.Agenda;

public interface AgendaService {

	public Agenda createAgenda(String title, String description);

	public Agenda findAgenda(Long id);

}
