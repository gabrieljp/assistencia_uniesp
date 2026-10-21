package br.edu.uniesp.assistencia.internal.equipamento.service;

import br.edu.uniesp.assistencia.internal.equipamento.dto.CriarEquipamentoRequest;
import br.edu.uniesp.assistencia.internal.cliente.entity.ClienteEntity;
import br.edu.uniesp.assistencia.internal.cliente.repository.ClienteRepository;
import br.edu.uniesp.assistencia.internal.equipamento.entity.EquipamentoEntity;
import br.edu.uniesp.assistencia.internal.equipamento.repository.EquipamentoRepository;
import br.edu.uniesp.assistencia.shared.exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public EquipamentoEntity cadastrar(CriarEquipamentoRequest request) {
        ClienteEntity cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente", request.clienteId()));

        // Instancia o equipamento via construtor com os dados do request
        EquipamentoEntity equipamento = new EquipamentoEntity(
                request.tipo(),
                request.marca(),
                request.modelo(),
                request.numeroSerie()
        );

        // Associa o cliente usando o método do modelo de domínio
        equipamento.associarCliente(cliente);

        return equipamentoRepository.save(equipamento);
    }

    @Transactional(readOnly = true)
    public List<EquipamentoEntity> listarTodos() {
        return equipamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EquipamentoEntity buscarPorId(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Equipamento", id));
    }
}
