CREATE TABLE ordens_servico (
                                id BIGSERIAL PRIMARY KEY,
                                numero_protocolo VARCHAR(30) NOT NULL UNIQUE,
                                cliente_id BIGINT NOT NULL,
                                equipamento_id BIGINT NOT NULL,
                                tecnico_id BIGINT,
                                status VARCHAR(30) NOT NULL,
                                descricao_problema TEXT NOT NULL,
                                data_abertura TIMESTAMP NOT NULL,
                                data_finalizacao TIMESTAMP,
                                data_entrega TIMESTAMP,
                                CONSTRAINT fk_ordem_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
                                CONSTRAINT fk_ordem_equipamento FOREIGN KEY (equipamento_id) REFERENCES equipamentos(id)
    -- Removido temporariamente o fk_ordem_tecnico até a tabela existir
);