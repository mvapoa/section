package br.com.sicredi.challenge.section.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CloseTimeValidatorTest {

	private CloseTimeValidator validator;

	@BeforeEach
	public void setUp() {
		validator = new CloseTimeValidator();
	}

	@Test
	public void shouldReturnTrueWhenTimeIsNull() {

		LocalDateTime value = null;

		assertTrue(validator.isValid(value, null));
	}

	@Test
	public void shouldReturnTryeWhenTimeGreaterThanNow() {

		LocalDateTime value = LocalDateTime.now().plusMinutes(1);

		assertTrue(validator.isValid(value, null));
	}

	@Test
	public void shouldReturnFalseWhenTimeLessThanNow() {

		LocalDateTime value = LocalDateTime.now().minusMinutes(1);

		assertFalse(validator.isValid(value, null));
	}

}
