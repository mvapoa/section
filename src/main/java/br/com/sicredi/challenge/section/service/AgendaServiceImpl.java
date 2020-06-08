package br.com.sicredi.challenge.section.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sicredi.challenge.section.dao.AgendaRepository;
import br.com.sicredi.challenge.section.entity.Agenda;
import br.com.sicredi.challenge.section.exception.BusinessException;

public class AgendaServiceImpl implements AgendaService {

	@Autowired
	private AgendaRepository repository;

	@Override
	public Agenda createAgenda(String title, String description) {

		Agenda agenda = new Agenda();
		agenda.setTitle(title);
		agenda.setDescription(description);
		return repository.save(agenda);
	}

	@Override
	public Agenda findAgenda(Long id) {

		Optional<Agenda> agenda = repository.findById(id);
		return Optional.of(agenda.get()).orElseThrow(() -> new BusinessException("${error.agenda.not.found}"));
	}

}
