package br.com.sicredi.challenge.section.service;

import org.springframework.stereotype.Service;

import br.com.sicredi.challenge.section.entity.Vote;

@Service
public interface VoteService {

	public Vote vote(Long idSession, String cpf, Boolean vote);

}
