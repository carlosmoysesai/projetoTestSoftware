package com.backend;

import com.backend.dto.PessoaDTO;
import com.backend.exceptions.NotFoundException;
import com.backend.model.Pessoa;
import com.backend.repository.PessoaRepository;
import com.backend.service.PessoaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        PessoaDTO dto = new PessoaDTO("John Doe", "john@example.com", "password123");
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("John Doe");
        pessoa.setEmail("john@example.com");

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa created = pessoaService.create(dto);

        assertEquals("John Doe", created.getNome());
        assertEquals("john@example.com", created.getEmail());
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    void testGetAll() {
        Pessoa pessoa1 = new Pessoa();
        Pessoa pessoa2 = new Pessoa();
        when(pessoaRepository.findAll()).thenReturn(Arrays.asList(pessoa1, pessoa2));

        List<Pessoa> pessoas = pessoaService.getAll();

        assertEquals(2, pessoas.size());
        verify(pessoaRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        Pessoa pessoa = new Pessoa();
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> found = pessoaService.getById(1L);

        assertTrue(found.isPresent());
        verify(pessoaRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetById_NotFound() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Pessoa> found = pessoaService.getById(1L);

        assertFalse(found.isPresent());
        verify(pessoaRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdate() throws NotFoundException {
        PessoaDTO dto = new PessoaDTO("Jane Doe", "jane@example.com", "password123");
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("John Doe");
        pessoa.setEmail("john@example.com");

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa updated = pessoaService.update(1L, dto);

        assertEquals("Jane Doe", updated.getNome());
        assertEquals("jane@example.com", updated.getEmail());
        verify(pessoaRepository, times(1)).findById(anyLong());
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    void testUpdate_NotFound() {
        PessoaDTO dto = new PessoaDTO("Jane Doe", "jane@example.com", "password123");
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.update(1L, dto));
        verify(pessoaRepository, times(1)).findById(anyLong());
        verify(pessoaRepository, times(0)).save(any(Pessoa.class));
    }

    @Test
    void testDelete() throws NotFoundException {
        Pessoa pessoa = new Pessoa();
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        pessoaService.delete(1L);

        verify(pessoaRepository, times(1)).findById(anyLong());
        verify(pessoaRepository, times(1)).delete(any(Pessoa.class));
    }

    @Test
    void testDelete_NotFound() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.delete(1L));
        verify(pessoaRepository, times(1)).findById(anyLong());
        verify(pessoaRepository, times(0)).delete(any(Pessoa.class));
    }
}
