package com.pricinghub.financeiro.data.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.pricinghub.financeiro.schema.VolumeCliente;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class VolumeClienteDataAccess {
	private final Logger logger = LoggerFactory.getLogger(VolumeClienteDataAccess.class);

	public VolumeClienteDataAccess(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    NamedParameterJdbcTemplate template;

    public void inserir(VolumeCliente volumeCliente) {
        try {	
            final String sql = "insert into volume_cliente(token,volume) values(:token,:volume)";
            logger.info(sql);

            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource param = new MapSqlParameterSource()
				.addValue("token", volumeCliente.getToken())
				.addValue("volume", volumeCliente.getVolume());
            template.update(sql, param, holder);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }
    
    public Integer obterIdVolumeDiario(String token) {
    	try {
    		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("token", token);
            return template.queryForObject(
              "Select id from volume_cliente where token = :token and created_at = current_date", namedParameters, Integer.class);
        } catch (EmptyResultDataAccessException e) {
    		return 0;
    	}
    }
    
    
    public void atualizar(Integer id, Float volume) {
        try {	
            final String sql = "update volume_cliente set volume=volume+:volume where id = :id";
            logger.info(sql);

            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", id)
				.addValue("volume", volume);
            template.update(sql, param, holder);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }
}
