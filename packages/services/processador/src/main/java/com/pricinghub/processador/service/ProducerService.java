package com.pricinghub.processador.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.pricinghub.processador.schema.OrdemAtivo;

@Component
public class ProducerService {

	private final Logger logger = LoggerFactory.getLogger(ProducerService.class);

	@Autowired
	private KafkaTemplate<String, OrdemAtivo> template;

	public void send(OrdemAtivo ordemAtivo) {
		template.send("ordem-ativo-precificar", ordemAtivo);
		logger.info(String.format("#### -> Produced message -> %s", ordemAtivo.toString()));
	}
}