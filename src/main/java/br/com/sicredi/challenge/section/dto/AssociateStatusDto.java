package br.com.sicredi.challenge.section.dto;

import java.io.Serializable;

public class AssociateStatusDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private StatusDto status;

	public AssociateStatusDto() {
		super();
	}

	public AssociateStatusDto(StatusDto status) {
		super();
		this.status = status;
	}

	public StatusDto getStatus() {
		return status;
	}

	public void setStatus(StatusDto status) {
		this.status = status;
	}

}
