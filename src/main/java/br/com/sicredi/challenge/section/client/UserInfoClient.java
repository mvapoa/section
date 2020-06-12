package br.com.sicredi.challenge.section.client;

import java.security.InvalidParameterException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import br.com.sicredi.challenge.section.dto.AssociateStatusDto;

@Service
public class UserInfoClient {

	private static final String URL_EXTERNAL = "https://user-info.herokuapp.com/users";

	private WebClient webClient;

	public UserInfoClient() {
		this.webClient = WebClient.builder()
				.baseUrl(URL_EXTERNAL)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public UserInfoClient(String baseUrl) {
		this.webClient = WebClient.builder()
				.baseUrl(baseUrl)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public AssociateStatusDto getAssociateStatus(String cpf) {

		try {
			return webClient.get().uri("/{cpf}", cpf).retrieve().bodyToMono(AssociateStatusDto.class).block();

		} catch (WebClientResponseException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new InvalidParameterException("error.vote.cpf.invalid");
			} else {
				throw ex;
			}

		}
	}

}
