package br.com.sicredi.challenge.section.service;

import br.com.sicredi.challenge.section.entity.Associate;

public interface AssociateService {

	public Associate findAndSaveAssociate(String cpf);

}
