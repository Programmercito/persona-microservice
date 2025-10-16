package com.devsu.finapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import com.devsu.finapp.common.jms.MessageProducer;
import com.devsu.finapp.common.jms.pojos.PersonaMessage;
import com.devsu.finapp.model.entities.Cliente;
import com.devsu.finapp.model.repositories.ClienteRespository;
import com.devsu.finapp.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class ClientTest {

	@Mock
	private ClienteRespository clienteRepository;

	@Mock
	private MessageProducer messageProducer;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private ClienteService clienteService;

	@Test
	void contextLoads() {
	}

	@Test
	void whenUpdateCliente_thenFieldsAreUpdatedCorrectly() {
		
		Cliente existingCliente = new Cliente();
		existingCliente.setId(1L);
		existingCliente.setNombres("Nombre Antiguo");
		existingCliente.setApellidos("Apellido Antiguo");
		existingCliente.setGenero("F");
		existingCliente.setFechaNacimiento(LocalDateTime.of(1990, 1, 1, 0, 0));
		existingCliente.setIdIdentificacion("12345678");
		existingCliente.setTipoIdentificacion("CC");
		existingCliente.setDireccion("Direccion Antigua");
		existingCliente.setTelefono("999888777");
		existingCliente.setEstado(true);

		Cliente clienteDetails = new Cliente();
		clienteDetails.setGenero("F");
		clienteDetails.setTelefono("999888777");
		clienteDetails.setEstado(false);

		when(clienteRepository.findById(1L)).thenReturn(Optional.of(existingCliente));
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

		clienteService.update(1L, clienteDetails);

		ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
		verify(clienteRepository).save(clienteCaptor.capture());
		Cliente capturedCliente = clienteCaptor.getValue();

		assertEquals("F", capturedCliente.getGenero());
		assertEquals("999888777", capturedCliente.getTelefono());
		assertEquals(false, capturedCliente.getEstado());

		assertEquals("Nombre Antiguo", capturedCliente.getNombres());
		assertEquals("Apellido Antiguo", capturedCliente.getApellidos());
		assertEquals(LocalDateTime.of(1990, 1, 1, 0, 0), capturedCliente.getFechaNacimiento());
		assertEquals("12345678", capturedCliente.getIdIdentificacion());
		assertEquals("CC", capturedCliente.getTipoIdentificacion());
		assertEquals("Direccion Antigua", capturedCliente.getDireccion());

		verify(messageProducer).send(any(String.class), any(PersonaMessage.class));
	}

}
