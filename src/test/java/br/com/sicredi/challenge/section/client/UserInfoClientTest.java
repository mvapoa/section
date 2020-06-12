package br.com.sicredi.challenge.section.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.InvalidParameterException;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import br.com.sicredi.challenge.section.dto.AssociateStatusDto;
import br.com.sicredi.challenge.section.dto.StatusDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@ExtendWith(MockitoExtension.class)
public class UserInfoClientTest {

	private final MockWebServer mockWebServer = new MockWebServer();

	private final UserInfoClient contentServiceImpl = new UserInfoClient(mockWebServer.url("localhost/").toString());

	@After
	public void tearDown() throws IOException {
		mockWebServer.shutdown();
	}

	@Test
	public void shouldReturnAbleToVoteWhenCpfIsValid() {

		mockWebServer.enqueue(new MockResponse().setResponseCode(200)
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.setBody("{\"status\": \"ABLE_TO_VOTE\"}"));

		AssociateStatusDto response = contentServiceImpl.getAssociateStatus("99999999999");
		assertEquals(StatusDto.ABLE_TO_VOTE, response.getStatus());
	}

	@Test
	public void shouldReturnUnableToVoteWhenCpfIsInvalid() {

		mockWebServer.enqueue(new MockResponse().setResponseCode(200)
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.setBody("{\"status\": \"UNABLE_TO_VOTE\"}"));

		AssociateStatusDto response = contentServiceImpl.getAssociateStatus("99999999999");
		assertEquals(StatusDto.UNABLE_TO_VOTE, response.getStatus());
	}

	@Test
	public void shouldThowExcpetionWhenExternalServiceReturnsNotFound() {

		mockWebServer.enqueue(new MockResponse().setResponseCode(404).setHeader(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON_VALUE));

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			contentServiceImpl.getAssociateStatus("99999999999");
		});

		assertTrue(thrown.getMessage().contains("error.vote.cpf.invalid"));
	}

}
