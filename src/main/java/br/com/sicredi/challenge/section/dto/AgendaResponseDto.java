package br.com.sicredi.challenge.section.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgendaResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String title;

	private String description;

	public AgendaResponseDto() {
		super();
	}

	public AgendaResponseDto(Long id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
	}

	@JsonProperty("code")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
