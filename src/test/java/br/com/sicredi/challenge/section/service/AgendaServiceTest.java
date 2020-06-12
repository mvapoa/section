package br.com.sicredi.challenge.section.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.sicredi.challenge.section.dao.AgendaRepository;
import br.com.sicredi.challenge.section.entity.Agenda;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

	@InjectMocks
	private AgendaServiceImpl service;

	@Mock
	private AgendaRepository repository;

	@Test
	public void shouldCreateAgenda() {

		String title = "title";
		String description = "description";

		Agenda agenda = new Agenda(null, title, description);

		given(repository.save(agenda)).willAnswer(i -> i.getArgument(0));

		Agenda response = service.createAgenda(title, description);

		assertEquals(response.toString(), agenda.toString());
	}

	@Test
	public void shouldReturnAgendaById() {

		Long id = 1l;
		String title = "title";
		String description = "description";

		Agenda agenda = new Agenda(id, title, description);

		given(repository.findById(id)).willReturn(Optional.of(agenda));

		Agenda response = service.findAgenda(id);

		assertEquals(response.toString(), agenda.toString());
	}

	@Test
	public void shouldThrowExceptionWhenNotFoundAgenda() {

		Long id = 1l;

		given(repository.findById(id)).willReturn(Optional.empty());

		InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
			service.findAgenda(id);
		});
		assertTrue(thrown.getMessage().contains("error.agenda.not.found"));
	}

}
