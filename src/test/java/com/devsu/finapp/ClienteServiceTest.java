package com.devsu.finapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devsu.finapp.common.exceptions.ResourceNotFoundException;
import com.devsu.finapp.common.jms.MessageProducer;
import com.devsu.finapp.common.jms.pojos.PersonaMessage;
import com.devsu.finapp.model.entities.Cliente;
import com.devsu.finapp.model.repositories.ClienteRespository;
import com.devsu.finapp.services.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRespository clienteRespository;

    @Mock
    private MessageProducer messageProducer;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombres("Jose Lema");
    }

    @Test
    void whenFindById_withValidId_thenReturnCliente() {
        // Arrange: Configuramos el mock para que devuelva nuestro cliente de prueba
        when(clienteRespository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act: Llamamos al método que queremos probar
        Cliente found = clienteService.findById(1L);

        // Assert: Verificamos que el resultado es el esperado
        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("Jose Lema", found.getNombres());
    }

    @Test
    void whenFindById_withInvalidId_thenThrowResourceNotFoundException() {
        // Arrange: Configuramos el mock para que devuelva un Optional vacío
        long invalidId = 99L;
        when(clienteRespository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert: Verificamos que se lanza la excepción esperada
        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.findById(invalidId);
        });
    }

    @Test
    void whenSaveCliente_thenReturnsSavedClienteAndCallsDependencies() {
        // Arrange
        cliente.setPassword("rawPassword");
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(clienteRespository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente savedCliente = clienteService.save(cliente);

        // Assert
        assertNotNull(savedCliente);
        assertEquals("encodedPassword", savedCliente.getPassword());

        // Verificamos que los métodos de las dependencias fueron llamados
        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(clienteRespository, times(1)).save(cliente);
        verify(messageProducer, times(1)).send(any(String.class), any(PersonaMessage.class));
    }

    @Test
    void whenUpdateCliente_withValidId_thenReturnsUpdatedCliente() {
        // Arrange
        Cliente clienteDetails = new Cliente();
        clienteDetails.setNombres("Jose Luis");
        clienteDetails.setDireccion("Nueva Direccion");

        // Simulamos que el cliente original existe
        when(clienteRespository.findById(1L)).thenReturn(Optional.of(cliente));
        // Simulamos la operación de guardado
        when(clienteRespository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente updatedCliente = clienteService.update(1L, clienteDetails);

        // Assert
        assertNotNull(updatedCliente);
        assertEquals("Jose Luis", updatedCliente.getNombres());
        assertEquals("Nueva Direccion", updatedCliente.getDireccion());

        // Verificamos que se llamó al repositorio para buscar y para guardar
        verify(clienteRespository, times(1)).findById(1L);
        verify(clienteRespository, times(1)).save(cliente);

        // Usamos ArgumentCaptor para verificar el contenido del mensaje enviado
        ArgumentCaptor<PersonaMessage> messageCaptor = ArgumentCaptor.forClass(PersonaMessage.class);
        verify(messageProducer).send(any(String.class), messageCaptor.capture());
        assertEquals("Jose Luis", messageCaptor.getValue().getNombres());
    }

    @Test
    void whenUpdateCliente_withInvalidId_thenThrowResourceNotFoundException() {
        // Arrange
        long invalidId = 99L;
        when(clienteRespository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.update(invalidId, new Cliente());
        });
    }
}