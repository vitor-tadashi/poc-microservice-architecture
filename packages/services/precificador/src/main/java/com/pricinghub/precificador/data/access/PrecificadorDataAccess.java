package com.pricinghub.precificador.data.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PrecificadorDataAccess {
	private final Logger logger = LoggerFactory.getLogger(PrecificadorDataAccess.class);

	public PrecificadorDataAccess() {
		try {
			precificacaoAtivo = new HashMap<String, HashMap<String,Float>>();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.getClass().getResourceAsStream("/precificacao-ativo.csv")));
			String line = null;

			while ((line = br.readLine()) != null) {
				String auxiliar1[] = line.split(",");
				String auxiliar2[] = auxiliar1[1].split(";");
				
				HashMap<String, Float> precificacao = precificacaoAtivo.get(auxiliar1[0]);
				
				if (precificacao == null) {
					HashMap<String, Float> precificador = new HashMap<String, Float>();
					precificador.put(auxiliar2[0], Float.parseFloat(auxiliar2[1]));
					
					precificacaoAtivo.put(auxiliar1[0], precificador);
				} else {
					precificacao.put(auxiliar2[0], Float.parseFloat(auxiliar2[1]));
				}
				
				logger.info(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	HashMap<String, HashMap<String,Float>> precificacaoAtivo;

	public Float obterPrecificacao(String ativo, String codigoPrecificacao) {
		return precificacaoAtivo.get(ativo).get(codigoPrecificacao);
	}
}
