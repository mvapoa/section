package br.com.sicredi.challenge.section.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class AgendaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{error.agenda.title.not.empty}")
	private String title;

	@NotEmpty(message = "{error.agenda.description.not.empty}")
	private String description;

	public AgendaDto() {
		super();
	}

	public AgendaDto(String title, String description) {
		super();
		this.title = title;
		this.description = description;
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
