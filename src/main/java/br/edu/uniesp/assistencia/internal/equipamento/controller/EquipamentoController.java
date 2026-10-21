package br.edu.uniesp.assistencia.internal.equipamento.controller;

import br.edu.uniesp.assistencia.internal.equipamento.dto.EquipamentoResponse;
import br.edu.uniesp.assistencia.internal.equipamento.entity.EquipamentoEntity;
import br.edu.uniesp.assistencia.internal.equipamento.service.EquipamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.uniesp.assistencia.internal.equipamento.dto.CriarEquipamentoRequest;

import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    @PostMapping
    public ResponseEntity<EquipamentoResponse> cadastrar(@Valid @RequestBody CriarEquipamentoRequest request) {
        EquipamentoEntity equipamento = equipamentoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(EquipamentoResponse.deEntidade(equipamento));
    }

    @GetMapping
    public ResponseEntity<List<EquipamentoResponse>> listarTodos() {
        List<EquipamentoResponse> lista = equipamentoService.listarTodos()
                .stream()
                .map(EquipamentoResponse::deEntidade)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> buscarPorId(@PathVariable Long id) {
        EquipamentoEntity equipamento = equipamentoService.buscarPorId(id);
        return ResponseEntity.ok(EquipamentoResponse.deEntidade(equipamento));
    }
}