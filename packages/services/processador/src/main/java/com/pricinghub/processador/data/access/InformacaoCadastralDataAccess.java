package com.pricinghub.processador.data.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InformacaoCadastralDataAccess {
	private final Logger logger = LoggerFactory.getLogger(InformacaoCadastralDataAccess.class);

	public InformacaoCadastralDataAccess() {
		try {
			dadosCadastrais = new HashMap<String, String>();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.getClass().getResourceAsStream("/informacao-cadastral.csv")));
			String line = null;

			while ((line = br.readLine()) != null) {
				String str[] = line.split(",");

				dadosCadastrais.put(str[0], str[1]);
				logger.info(String.format("Ativo %s -> Código precificação %s", str[0], str[1]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	HashMap<String, String> dadosCadastrais;

	public String obterCodigoPrecificacaoAtivo(String ativo) {
		return dadosCadastrais.get(ativo);
	}
}
