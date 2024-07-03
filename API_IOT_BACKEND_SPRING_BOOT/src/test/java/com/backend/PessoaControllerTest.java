package com.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.backend.controllers.PessoaController;
import com.backend.dto.PessoaDTO;
import com.backend.exceptions.NotFoundException;
import com.backend.model.Pessoa;
import com.backend.service.PessoaService;

class PessoaControllerTest {

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pessoaController).build();
    }

    @Test
    void testCreate() {
        PessoaDTO dto = new PessoaDTO(null, null, null);
        Pessoa pessoa = new Pessoa();
        when(pessoaService.create(any(PessoaDTO.class))).thenReturn(pessoa);

        ResponseEntity<Object> response = pessoaController.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(pessoaService, times(1)).create(any(PessoaDTO.class));
    }

    @Test
    void testGetAll() {
        Pessoa pessoa1 = new Pessoa();
        Pessoa pessoa2 = new Pessoa();
        when(pessoaService.getAll()).thenReturn(Arrays.asList(pessoa1, pessoa2));

        List<Pessoa> pessoas = pessoaController.getAll();

        assertEquals(2, pessoas.size());
        verify(pessoaService, times(1)).getAll();
    }

    @Test
    void testGetById() {
        Pessoa pessoa = new Pessoa();
        when(pessoaService.getById(anyLong())).thenReturn(Optional.of(pessoa));

        ResponseEntity<Object> response = pessoaController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pessoaService, times(1)).getById(anyLong());
    }

    @Test
    void testGetById_NotFound() {
        when(pessoaService.getById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = pessoaController.getById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(pessoaService, times(1)).getById(anyLong());
    }

    @Test
    void testUpdate() throws NotFoundException {
        PessoaDTO dto = new PessoaDTO(null, null, null);
        Pessoa pessoa = new Pessoa();
        when(pessoaService.update(anyLong(), any(PessoaDTO.class))).thenReturn(pessoa);

        ResponseEntity<Object> response = pessoaController.update(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pessoaService, times(1)).update(anyLong(), any(PessoaDTO.class));
    }

    @Test
    void testUpdate_NotFound() throws NotFoundException {
        PessoaDTO dto = new PessoaDTO(null, null, null);
        when(pessoaService.update(anyLong(), any(PessoaDTO.class))).thenThrow(new NotFoundException("Pessoa não encontrada"));

        ResponseEntity<Object> response = pessoaController.update(1L, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(pessoaService, times(1)).update(anyLong(), any(PessoaDTO.class));
    }

    @Test
    void testDelete() throws NotFoundException {
        doNothing().when(pessoaService).delete(anyLong());

        ResponseEntity<Object> response = pessoaController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pessoaService, times(1)).delete(anyLong());
    }

    @Test
    void testDelete_NotFound() throws NotFoundException {
        doThrow(new NotFoundException("Pessoa não encontrada")).when(pessoaService).delete(anyLong());

        ResponseEntity<Object> response = pessoaController.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(pessoaService, times(1)).delete(anyLong());
    }
}
