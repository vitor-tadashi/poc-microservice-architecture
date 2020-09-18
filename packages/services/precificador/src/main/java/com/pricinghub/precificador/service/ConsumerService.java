package com.pricinghub.precificador.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pricinghub.precificador.data.access.PrecificadorDataAccess;
import com.pricinghub.processador.schema.OrdemAtivo;

@Service
public class ConsumerService {

	private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private PrecificadorDataAccess informacaoCadastralDataAccess;

	@Autowired
	private ProducerService producerService;

	@KafkaListener(topics = "ordem-ativo-precificar", groupId = "precificador-ordem", containerFactory = "listenerContainerFactory")
	public void processarOrdemAtivo(OrdemAtivo ordemAtivo) throws IOException {
		logger.info(String.format("#### -> Consumed message -> %s", ordemAtivo.toString()));

		Float preco = informacaoCadastralDataAccess.obterPrecificacao(ordemAtivo.getAtivo(), ordemAtivo.getCodigoPrecificacao());

		ordemAtivo.setPreco(preco);
		ordemAtivo.setTotal(ordemAtivo.getQuantidade() * ordemAtivo.getPreco());
        
        logger.info(String.format("#### -> Preço %s -> Total %s", preco,
				ordemAtivo.getTotal()));

		producerService.send(ordemAtivo);
	}
}