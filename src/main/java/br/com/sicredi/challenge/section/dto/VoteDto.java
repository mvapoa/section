package br.com.sicredi.challenge.section.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class VoteDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{error.session.idSession.not.empty}")
	private Long idSession;

	@NotEmpty(message = "{error.session.cpf.not.empty}")
	// TODO create validation CPF
	private String cpf;

	@NotEmpty(message = "{error.session.vote.not.empty}")
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
