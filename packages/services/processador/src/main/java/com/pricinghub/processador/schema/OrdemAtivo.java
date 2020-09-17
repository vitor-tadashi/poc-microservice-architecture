package com.pricinghub.processador.schema;

public class OrdemAtivo {

	private long quantidade;

	private String ativo;

	private String token;

	private String codigoPrecificacao;

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getCodigoPrecificacao() {
		return codigoPrecificacao;
	}

	public void setCodigoPrecificacao(String codigoPrecificacao) {
		this.codigoPrecificacao = codigoPrecificacao;
	}

	@Override
	public String toString() {
		return String.format("Ativo: %s, Quantidade: %s, Token: %s, Código Precificação %s", ativo, quantidade, token, codigoPrecificacao);
	}
}