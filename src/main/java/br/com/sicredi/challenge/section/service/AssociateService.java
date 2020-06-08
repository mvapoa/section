package br.com.sicredi.challenge.section.service;

import org.springframework.stereotype.Service;

import br.com.sicredi.challenge.section.entity.Associate;

@Service
public interface AssociateService {

	public Associate findAndSaveAssociate(String cpf);

}
