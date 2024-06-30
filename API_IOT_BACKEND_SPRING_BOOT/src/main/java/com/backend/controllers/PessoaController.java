package com.backend.controllers;

import com.backend.exceptions.NotFoundException;
import com.backend.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.dto.PessoaDTO;
import com.backend.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @Operation(summary = "Cria uma nova pessoa")
    @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso")
    @CrossOrigin(origins = "http://localhost:80")
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody PessoaDTO dto) {
        try {
            var res = pessoaService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PostMapping("/send-message")
    public String sendMessageToQueue(@RequestBody String message) {
        rabbitMQSender.send(message);
        return "Mensagem enviada para RabbitMQ: " + message;
    }

    @Operation(summary = "Obtém todas as pessoas")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    @CrossOrigin(origins = "http://localhost:80")
    @GetMapping
    public List<Pessoa> getAll() {
        return pessoaService.getAll();
    }

    @Operation(summary = "Obtém uma pessoa pelo ID")
    @ApiResponse(responseCode = "200", description = "Pessoa encontrada")
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    @CrossOrigin(origins = "http://localhost:80")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) {
        var person = pessoaService.getById(id);
        return person.isPresent() ? ResponseEntity.ok().body(person.get()) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualiza uma pessoa")
    @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    @CrossOrigin(origins = "http://localhost:80")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") long id, @RequestBody PessoaDTO dto) {
        try {
            return ResponseEntity.ok().body(pessoaService.update(id, dto));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Exclui uma pessoa")
    @ApiResponse(responseCode = "200", description = "Pessoa excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    @CrossOrigin(origins = "http://localhost:80")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") long id) {
        try {
            pessoaService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
