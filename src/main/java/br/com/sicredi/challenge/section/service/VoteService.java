package br.com.sicredi.challenge.section.service;

import br.com.sicredi.challenge.section.entity.Vote;

public interface VoteService {

	public Vote vote(Long idSession, String cpf, Boolean vote);

}
