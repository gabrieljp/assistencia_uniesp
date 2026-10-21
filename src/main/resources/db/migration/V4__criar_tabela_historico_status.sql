CREATE TABLE historico_status (
                                  id BIGSERIAL PRIMARY KEY,
                                  ordem_servico_id BIGINT NOT NULL,
                                  status_anterior VARCHAR(30),
                                  novo_status VARCHAR(30) NOT NULL,
                                  responsavel VARCHAR(100),
                                  observacao TEXT,
                                  data_alteracao TIMESTAMP NOT NULL,
                                  CONSTRAINT fk_historico_ordem FOREIGN KEY (ordem_servico_id) REFERENCES ordens_servico(id)
);