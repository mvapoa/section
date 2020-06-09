package br.com.sicredi.challenge.section.service;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.challenge.section.dao.AgendaRepository;
import br.com.sicredi.challenge.section.entity.Agenda;

@Service
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
		if (!agenda.isPresent()) {
			throw new InvalidParameterException("error.agenda.not.found");
		}
		return agenda.get();
	}

}
