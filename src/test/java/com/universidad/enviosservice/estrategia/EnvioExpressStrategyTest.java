package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioExpressStrategyTest {

    private final EnvioExpressStrategy estrategia = new EnvioExpressStrategy();

    @ParameterizedTest
    @CsvSource({
        "3.0, 100.0, 156.0",    // pesoKg <= 5: (100 * 1.2) * 1.3 = 120 * 1.3 = 156.0
        "10.0, 100.0, 165.75",  // 5 < peso <= 20: (120 + 5 * 1.5) * 1.3 = 127.5 * 1.3 = 165.75
        "25.0, 100.0, 198.25",  // peso > 20: (120 + 15 * 1.5 + 5 * 2.0) * 1.3 = 152.5 * 1.3 = 198.25
        "3.0, 600.0, 968.5"     // dist > 500: (600 * 1.2 + 25) * 1.3 = 745 * 1.3 = 968.5
    })
    void calculaCostoExpress(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }
}
