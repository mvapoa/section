package br.com.sicredi.challenge.section.service;

import org.springframework.stereotype.Service;

import br.com.sicredi.challenge.section.entity.Agenda;

@Service
public interface AgendaService {

	public Agenda createAgenda(String title, String description);

	public Agenda findAgenda(Long id);

}
