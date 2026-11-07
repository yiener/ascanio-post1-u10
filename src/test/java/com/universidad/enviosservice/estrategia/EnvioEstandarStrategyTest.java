package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioEstandarStrategyTest {

    private final EnvioEstandarStrategy estrategia = new EnvioEstandarStrategy();

    @ParameterizedTest
    @CsvSource({
        "3.0, 100.0, 50.0",     // pesoKg <= 5, dist <= 500 (100 * 0.5)
        "10.0, 100.0, 54.0",    // 5 < pesoKg <= 20 (50 + 5 * 0.8)
        "25.0, 100.0, 68.0",    // pesoKg > 20 (50 + 12 + 5 * 1.2)
        "3.0, 600.0, 310.0"     // dist > 500 (300 + 10)
    })
    void calculaCostoEstandar(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @Test
    void calculaCostoDistanciaMayorA500ConPesoAlto() {
        // dist 600 (>500 -> +10), peso 25 (>20 -> 15*0.8 + 5*1.2 = 18)
        // costo = 600*0.5 + 18 + 10 = 328.0
        assertEquals(328.0, estrategia.calcular(25.0, 600.0), 0.01);
    }
}
