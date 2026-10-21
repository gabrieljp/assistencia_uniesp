package br.edu.uniesp.assistencia.internal.equipamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarEquipamentoRequest(
        @NotBlank(message = "O tipo do equipamento é obrigatório")
        String tipo,

        @NotBlank(message = "A marca é obrigatória")
        String marca,

        @NotBlank(message = "O modelo é obrigatório")
        String modelo,

        String numeroSerie,

        @NotNull(message = "O ID do cliente proprietário é obrigatório")
        Long clienteId
) {}