package br.com.sicredi.challenge.section.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.challenge.section.dao.AssociateRepository;
import br.com.sicredi.challenge.section.entity.Associate;

@Service
public class AssociateServiceImpl implements AssociateService {

	@Autowired
	private AssociateRepository repository;

	@Override
	public Associate findAndSaveAssociate(String cpf) {

		Optional<Associate> associate = repository.findByCpf(cpf);
		if (!associate.isPresent()) {
			return repository.save(new Associate(null, cpf));
		}
		return associate.get();
	}

}
