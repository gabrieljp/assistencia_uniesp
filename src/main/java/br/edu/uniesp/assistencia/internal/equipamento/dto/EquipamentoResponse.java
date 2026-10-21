package br.edu.uniesp.assistencia.internal.equipamento.dto;

public record EquipamentoResponse(
        Long id,
        String tipo,
        String marca,
        String modelo,
        String numeroSerie,
        Long clienteId
) {
    public static EquipamentoResponse deEntidade(br.edu.uniesp.assistencia.internal.equipamento.entity.EquipamentoEntity entidade) {
        return new EquipamentoResponse(
                entidade.getId(),
                entidade.getTipo(),
                entidade.getMarca(),
                entidade.getModelo(),
                entidade.getNumeroSerie(),
                entidade.getCliente().getId()
        );
    }
}