package br.com.sicredi.challenge.section.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

public class VoteDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "{error.vote.idSession.not.empty}")
	private Long idSession;

	@NotEmpty(message = "{error.vote.cpf.not.empty}")
	@CPF(message = "{error.vote.cpf.invalid}")
	private String cpf;

	@NotNull(message = "{error.vote.vote.not.empty}")
	private Boolean vote;

	public VoteDto() {
		super();
	}

	public VoteDto(Long idSession, String cpf, Boolean vote) {
		super();
		this.idSession = idSession;
		this.cpf = cpf;
		this.vote = vote;
	}

	public Long getIdSession() {
		return idSession;
	}

	public void setIdSession(Long idSession) {
		this.idSession = idSession;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getVote() {
		return vote;
	}

	public void setVote(Boolean vote) {
		this.vote = vote;
	}

}
