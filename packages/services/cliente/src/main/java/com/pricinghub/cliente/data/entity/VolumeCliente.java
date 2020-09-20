package com.pricinghub.cliente.data.entity;

public class VolumeCliente {
	public VolumeCliente(String token, Float volume) {
		this.token = token;
		this.volume = volume;	
	}
	
	private String token;

	private Float volume;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}
}
