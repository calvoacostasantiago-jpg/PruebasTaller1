package pronosticos.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pronosticos.CalculadoraPronosticos;

class MseTest {

    private static final double TOLERANCIA = 0.01;
    private final CalculadoraPronosticos calculadora = new CalculadoraPronosticos();

    @Test
    @DisplayName("Calcula el error cuadratico medio con pronosticos redondeados")
    void calculaElErrorCuadraticoMedio() {
        double resultado = calculadora.mse(
                List.of(141.0, 138.0, 150.0), List.of(126.67, 133.67, 135.67));

        // (205.3489 + 18.7489 + 205.3489) / 3 = 143.1489.
        assertEquals(143.1489, resultado, TOLERANCIA);
    }
}
