package br.edu.uniesp.assistencia.internal.ordemservico.service;

import br.edu.uniesp.assistencia.internal.cliente.entity.ClienteEntity;
import br.edu.uniesp.assistencia.internal.cliente.repository.ClienteRepository;
import br.edu.uniesp.assistencia.internal.equipamento.entity.EquipamentoEntity;
import br.edu.uniesp.assistencia.internal.equipamento.repository.EquipamentoRepository;
import br.edu.uniesp.assistencia.internal.ordemservico.dto.CriarOrdemServicoRequest;
import br.edu.uniesp.assistencia.internal.ordemservico.dto.CriarOrdemServicoResponse;
import br.edu.uniesp.assistencia.internal.ordemservico.entity.OrdemServicoEntity;
import br.edu.uniesp.assistencia.internal.ordemservico.repository.OrdemServicoRepository;
import br.edu.uniesp.assistencia.shared.exception.RegraNegocioException;
import br.edu.uniesp.assistencia.shared.exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;
    private final ClienteRepository clienteRepository;
    private final EquipamentoRepository equipamentoRepository;

    @Transactional
    public CriarOrdemServicoResponse criar(CriarOrdemServicoRequest request) {
        // 1. Valida se o cliente existe
        ClienteEntity cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente", request.clienteId()));

        // 2. Valida se o cliente está ativo
        if (!cliente.isAtivo()) {
            throw new RegraNegocioException("Não é permitido abrir Ordem de Serviço para um cliente inativo.");
        }

        // 3. Valida se o equipamento existe
        EquipamentoEntity equipamento = equipamentoRepository.findById(request.equipamentoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Equipamento", request.equipamentoId()));

        // 4. Valida se o equipamento pertence ao cliente indicado
        if (!equipamento.getCliente().getId().equals(cliente.getId())) {
            throw new RegraNegocioException("O equipamento informado não pertence ao cliente indicado.");
        }

        // 5. Gera número de protocolo
        String dataStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomSuffix = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        String numeroProtocolo = "OS-" + dataStr + "-" + randomSuffix;

        // 6. Cria a entidade e salva (com os parâmetros na ordem correta da entidade)
        OrdemServicoEntity os = new OrdemServicoEntity(
                numeroProtocolo,
                cliente,
                equipamento,
                request.descricaoProblema()
        );

        OrdemServicoEntity salva = ordemServicoRepository.save(os);

        return new CriarOrdemServicoResponse(
                salva.getId(),
                salva.getNumeroProtocolo(),
                salva.getStatus(),
                salva.getDataAbertura()
        );
    }
}