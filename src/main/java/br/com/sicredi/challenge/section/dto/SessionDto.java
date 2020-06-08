package br.com.sicredi.challenge.section.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import br.com.sicredi.challenge.section.validation.CloseTimeThanNow;

public class SessionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{error.session.idAgenda.not.empty}")
	private Long idAgenda;

	@CloseTimeThanNow(message = "{error.session.closeTime.than.now}")
	private LocalDateTime closeTime;

	public SessionDto() {
		super();
	}

	public SessionDto(Long idAgenda, LocalDateTime closeTime) {
		super();
		this.idAgenda = idAgenda;
		this.closeTime = closeTime;
	}

	public Long getIdAgenda() {
		return idAgenda;
	}

	public void setIdAgenda(Long idAgenda) {
		this.idAgenda = idAgenda;
	}

	public LocalDateTime getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(LocalDateTime closeTime) {
		this.closeTime = closeTime;
	}

}
