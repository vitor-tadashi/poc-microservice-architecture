package com.pricinghub.processador.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pricinghub.processador.data.access.InformacaoCadastralDataAccess;
import com.pricinghub.processador.schema.OrdemAtivo;

@Service
public class ConsumerService {

	private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private InformacaoCadastralDataAccess informacaoCadastralDataAccess;

	@Autowired
	private ProducerService producerService;

	@KafkaListener(topics = "ordem-ativo", groupId = "processador-ativo", containerFactory = "listenerContainerFactory")
	public void processarOrdemAtivo(OrdemAtivo ordemAtivo) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", ordemAtivo.toString()));

		String codigoPrecificacao = informacaoCadastralDataAccess.obterCodigoPrecificacaoAtivo(ordemAtivo.getAtivo());

        ordemAtivo.setCodigoPrecificacao(codigoPrecificacao);
        
        logger.info(String.format("#### -> Código de precificação do ativo %s -> %s", codigoPrecificacao,
				ordemAtivo.getCodigoPrecificacao()));

		producerService.send(ordemAtivo);
	}
}