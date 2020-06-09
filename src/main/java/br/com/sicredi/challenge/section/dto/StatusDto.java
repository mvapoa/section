package br.com.sicredi.challenge.section.dto;

public enum StatusDto {

	ABLE_TO_VOTE("ABLE_TO_VOTE"), UNABLE_TO_VOTE("UNABLE_TO_VOTE");

	private final String status;

	private StatusDto(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}

}
