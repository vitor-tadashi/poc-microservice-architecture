package com.pricinghub.agendador.data.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.pricinghub.processador.schema.OrdemAtivo;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class OrdemPrecificadaDataAccess {
	private final Logger logger = LoggerFactory.getLogger(OrdemPrecificadaDataAccess.class);

	public OrdemPrecificadaDataAccess(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    NamedParameterJdbcTemplate template;

    public void inserir(OrdemAtivo ordemAtivo) {
        try {	
            final String sql = "insert into ordem_ativo(ativo, token, quantidade, codigo_precificacao, preco, total) values(:ativo,:token,:quantidade,:codigo_precificacao,:preco,:total)";
            logger.info(sql);

            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource param = new MapSqlParameterSource()
				.addValue("ativo", ordemAtivo.getAtivo())
				.addValue("token", ordemAtivo.getToken())
				.addValue("quantidade", ordemAtivo.getQuantidade())
	            .addValue("codigo_precificacao", ordemAtivo.getCodigoPrecificacao())
				.addValue("preco", ordemAtivo.getPreco())
	            .addValue("total", ordemAtivo.getTotal());
            template.update(sql, param, holder);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }
}
