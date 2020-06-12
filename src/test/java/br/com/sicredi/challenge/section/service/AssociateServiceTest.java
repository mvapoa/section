package br.com.sicredi.challenge.section.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.sicredi.challenge.section.dao.AssociateRepository;
import br.com.sicredi.challenge.section.entity.Associate;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {

	@InjectMocks
	private AssociateServiceImpl service;

	@Mock
	private AssociateRepository repository;

	@Test
	public void shouldReturnAssociateByCpf() {

		Long id = 1l;
		String cpf = "99999999999";

		Associate associate = new Associate(id, cpf);

		given(repository.findByCpf(cpf)).willReturn(Optional.of(associate));

		Associate response = service.findAndSaveAssociate(cpf);

		assertEquals(response.toString(), associate.toString());
	}

	@Test
	public void shouldSaveAndReturnAssociateByCpf() {

		String cpf = "99999999999";

		Associate associate = new Associate(null, cpf);

		given(repository.findByCpf(cpf)).willReturn(Optional.empty());
		given(repository.save(associate)).willAnswer(i -> i.getArgument(0));

		Associate response = service.findAndSaveAssociate(cpf);

		assertEquals(response.toString(), associate.toString());
	}

}
