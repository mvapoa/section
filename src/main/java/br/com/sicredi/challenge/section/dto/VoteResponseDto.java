package br.com.sicredi.challenge.section.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Boolean vote;

	public VoteResponseDto() {
		super();
	}

	public VoteResponseDto(Long id, Boolean vote) {
		super();
		this.id = id;
		this.vote = vote;
	}

	@JsonProperty("code")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getVote() {
		return vote;
	}

	public void setVote(Boolean vote) {
		this.vote = vote;
	}

}
