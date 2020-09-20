package com.pricinghub.agendador.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pricinghub.agendador.data.access.OrdemPrecificadaDataAccess;
import com.pricinghub.processador.schema.OrdemAtivo;

@Service
public class ConsumerService {

	private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private OrdemPrecificadaDataAccess ordemPrecificadaDataAccess;

	@KafkaListener(topics = "ordem-ativo-precificado", groupId = "agendador-ativo", containerFactory = "listenerContainerFactory")
	public void processarOrdemAtivo(OrdemAtivo ordemAtivo) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", ordemAtivo.toString()));
		ordemPrecificadaDataAccess.inserir(ordemAtivo);
	}
}