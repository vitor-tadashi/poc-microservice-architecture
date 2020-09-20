package com.pricinghub.cliente.data.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.pricinghub.cliente.data.entity.VolumeCliente;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@Repository
public class VolumeClienteDataAccess {
	private final Logger logger = LoggerFactory.getLogger(VolumeClienteDataAccess.class);

	public VolumeClienteDataAccess(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	NamedParameterJdbcTemplate template;

	public VolumeCliente obterVolumeDiario(String token) {
		try {
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("token", token);
			String sql = "SELECT token, volume from volume_cliente WHERE token = :token and created_at = current_date";

			return template.queryForObject(sql, namedParameters,
					(rs, rowNum) -> new VolumeCliente(rs.getString("token"), rs.getFloat("volume")));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
