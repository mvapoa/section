package br.com.sicredi.challenge.section.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sicredi.challenge.section.dao.AssociateRepository;
import br.com.sicredi.challenge.section.entity.Associate;

public class AssociateServiceImpl implements AssociateService {

	@Autowired
	private AssociateRepository repository;

	@Override
	public Associate findAndSaveAssociate(String cpf) {

		Optional<Associate> associate = repository.findByCpf(cpf);
		return Optional.of(associate.get()).orElse(repository.save(new Associate(null, cpf)));
	}

}
