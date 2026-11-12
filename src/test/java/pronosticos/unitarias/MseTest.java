package pronosticos.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("Rechaza series con distinta cantidad de periodos")
    void rechazaSeriesDeDistintaLongitud() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.mse(List.of(10.0, 20.0), List.of(10.0)));

        assertEquals("Las series deben tener el mismo número de periodos", error.getMessage());
    }

    @Test
    @DisplayName("Rechaza series vacias porque el promedio no esta definido")
    void rechazaSeriesVacias() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.mse(List.of(), List.of()));

        assertEquals("Se necesita al menos un periodo", error.getMessage());
    }

    @Test
    @DisplayName("El error cuadratico es cero cuando el pronostico es exacto")
    void esCeroCuandoElPronosticoEsExacto() {
        assertEquals(0.0, calculadora.mse(List.of(100.0, 200.0), List.of(100.0, 200.0)), TOLERANCIA);
    }
}
