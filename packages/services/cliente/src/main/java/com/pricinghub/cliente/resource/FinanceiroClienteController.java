package com.pricinghub.cliente.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pricinghub.cliente.data.access.VolumeClienteDataAccess;
import com.pricinghub.cliente.data.entity.VolumeCliente;

@RestController
@RequestMapping("/api/v1/clientes")
public class FinanceiroClienteController {
	
	private final Logger logger = LoggerFactory.getLogger(FinanceiroClienteController.class);

	@Autowired
	private VolumeClienteDataAccess ordemPrecificadaDataAccess;
	
    @GetMapping("/{token}/volume/diario")
    public ResponseEntity<VolumeCliente> one(@PathVariable String token) {
    	logger.info(token);
        
        return ResponseEntity.ok().body(ordemPrecificadaDataAccess.obterVolumeDiario(token));
    }    
}