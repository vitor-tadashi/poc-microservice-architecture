package com.pricinghub.financeiro.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pricinghub.financeiro.data.access.VolumeClienteDataAccess;
import com.pricinghub.financeiro.schema.VolumeCliente;
import com.pricinghub.processador.schema.OrdemAtivo;

@Service
public class ConsumerService {

	private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private VolumeClienteDataAccess ordemPrecificadaDataAccess;

	@KafkaListener(topics = "ordem-ativo-precificado", groupId = "financeiro", containerFactory = "listenerContainerFactory")
	public void processarOrdemAtivo(OrdemAtivo ordemAtivo) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", ordemAtivo.toString()));
		
		
		Integer id = ordemPrecificadaDataAccess.obterIdVolumeDiario(ordemAtivo.getToken());
		logger.info(String.format("#### -> Id volume diário -> %s", id));
		
		if (id > 0) {
			ordemPrecificadaDataAccess.atualizar(id, ordemAtivo.getTotal());
		} else {
			ordemPrecificadaDataAccess.inserir(new VolumeCliente(ordemAtivo.getToken(), ordemAtivo.getTotal()));
		}
	}
}