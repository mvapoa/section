package br.com.sicredi.challenge.section.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long sessionId;

	private Boolean winner;

	private Integer totalTrue;

	private Integer totalFalse;

	public ResultResponseDto() {
		super();
	}

	public ResultResponseDto(Long id, Long sessionId, Boolean winner, Integer totalTrue, Integer totalFalse) {
		super();
		this.id = id;
		this.sessionId = sessionId;
		this.winner = winner;
		this.totalTrue = totalTrue;
		this.totalFalse = totalFalse;
	}

	@JsonProperty("code")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("codeSession")
	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	public Integer getTotalTrue() {
		return totalTrue;
	}

	public void setTotalTrue(Integer totalTrue) {
		this.totalTrue = totalTrue;
	}

	public Integer getTotalFalse() {
		return totalFalse;
	}

	public void setTotalFalse(Integer totalFalse) {
		this.totalFalse = totalFalse;
	}

}
