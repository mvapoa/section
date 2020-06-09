package br.com.sicredi.challenge.section.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private LocalDateTime openTime;

	private LocalDateTime closeTime;

	public SessionResponseDto() {
		super();
	}

	public SessionResponseDto(Long id, LocalDateTime openTime, LocalDateTime closeTime) {
		super();
		this.id = id;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}

	@JsonProperty("code")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getOpenTime() {
		return openTime;
	}

	public void setOpenTime(LocalDateTime openTime) {
		this.openTime = openTime;
	}

	public LocalDateTime getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(LocalDateTime closeTime) {
		this.closeTime = closeTime;
	}

}
