package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioInternacionalStrategyTest {

    private final EnvioInternacionalStrategy estrategia = new EnvioInternacionalStrategy();

    @ParameterizedTest
    @CsvSource({
        "3.0, 1000.0, 1850.0",   // peso <= 5, dist <= 5000: 1000 * 1.8 + 50 = 1850
        "10.0, 1000.0, 1865.0",  // 5 < peso <= 20: 1850 + 5 * 3.0 = 1865
        "25.0, 1000.0, 1917.5",  // peso > 20: 1850 + 15 * 3.0 + 5 * 4.5 = 1850 + 45 + 22.5 = 1917.5
        "3.0, 6000.0, 10950.0"   // dist > 5000 (+100): 6000 * 1.8 + 50 + 100 = 10800 + 150 = 10950
    })
    void calculaCostoInternacional(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }
}
