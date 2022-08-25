package com.example.cambioservice.controllers;

import com.example.cambioservice.entities.Cambio;
import com.example.cambioservice.repostitories.CambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@RestController
@RequestMapping("/cambio-service")
public class CambioController {

    @Autowired
    private Environment environment;

    @Autowired
    private CambioRepository repository;

    @GetMapping("/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable("amount")BigDecimal amount,
                            @PathVariable("from") String from,
                            @PathVariable("to") String to){

        String port = environment.getProperty("local.server.port");

        Cambio cambio = repository.findByFromAndTo(from, to);

        if(Objects.isNull(cambio)) throw new RuntimeException("Currency unsupported");

        cambio.setEnvironment(port);

        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);

        cambio.setConvertValue(convertedValue.setScale(2, RoundingMode.CEILING));

        return cambio;
    }

}
