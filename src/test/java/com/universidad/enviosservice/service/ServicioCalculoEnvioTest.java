package com.universidad.enviosservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicioCalculoEnvioTest {

    private final ServicioCalculoEnvio servicio = new ServicioCalculoEnvio();

    @Test
    void calculaEnvioEstandarPesoBajo() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", false);
        assertEquals(50.0, costo, 0.01);
    }

    @Test
    void aplicaDescuentoClientePremium() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "PREMIUM", false);
        assertEquals(45.0, costo, 0.01);
    }

    @Test
    void aplicaDescuentoClienteCorporativo() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "CORPORATIVO", false);
        // 50.0 * 0.85 = 42.5
        assertEquals(42.5, costo, 0.01);
    }

    @Test
    void aplicaRecargoPorFragilidad() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", true);
        // 50.0 + 50.0 * 0.15 = 57.5
        assertEquals(57.5, costo, 0.01);
    }

    @Test
    void aplicaFragilidadYDescuentoCorporativoSimultaneo() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "CORPORATIVO", true);
        // 50.0 * 1.15 = 57.5 -> 57.5 * 0.85 = 48.875
        assertEquals(48.875, costo, 0.01);
    }

    @ParameterizedTest
    @CsvSource({
        "EXPRESS, 3.0, 100.0, REGULAR, false, 156.0",
        "OVERNIGHT, 3.0, 100.0, REGULAR, false, 220.0",
        "INTERNACIONAL, 3.0, 1000.0, REGULAR, false, 1850.0"
    })
    void delegaCorrectamenteEnCadaEstrategia(String metodo, double peso, double dist, String cliente, boolean fragil, double esperado) {
        assertEquals(esperado, servicio.calcularCosto(metodo, peso, dist, cliente, fragil), 0.01);
    }

    @Test
    void rechazaMetodoEnvioNoSoportado() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> servicio.calcularCosto("DRON_AEREO", 1.0, 10.0, "REGULAR", false));
        assertEquals("Metodo de envio no soportado: DRON_AEREO", ex.getMessage());
    }
}
