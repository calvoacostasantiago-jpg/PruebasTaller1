package pronosticos.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pronosticos.CalculadoraPronosticos;

class MapeTest {

    private static final double TOLERANCIA = 0.01;
    private final CalculadoraPronosticos calculadora = new CalculadoraPronosticos();

    @Test
    @DisplayName("Calcula el error porcentual del ejemplo de Produccion")
    void calculaElErrorPorcentual() {
        double resultado = calculadora.mape(
                List.of(141.0, 138.0, 150.0), List.of(126.67, 133.67, 135.67));

        assertEquals(7.62, resultado, TOLERANCIA);
    }

    @Test
    @DisplayName("Excluye del promedio los periodos con demanda cero")
    void excluyeLosPeriodosConDemandaCero() {
        double resultado = calculadora.mape(List.of(80.0, 0.0, 95.0), List.of(80.0, 0.0, 100.0));

        // (0 + 5 / 95 * 100) / 2 = 2.631578947...
        assertEquals(2.63, resultado, TOLERANCIA);
    }

    @Test
    @DisplayName("Rechaza el MAPE cuando todas las demandas son cero")
    void rechazaTodasLasDemandasEnCero() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.mape(List.of(0.0, 0.0), List.of(10.0, 20.0)));

        assertEquals("El MAPE no está definido: todos los periodos tienen demanda 0", error.getMessage());
    }

    @Test
    @DisplayName("El MAPE es cero con pronosticos exactos")
    void esCeroConPronosticosExactos() {
        assertEquals(0.0, calculadora.mape(List.of(20.0, 40.0), List.of(20.0, 40.0)), TOLERANCIA);
    }

    @Test
    @DisplayName("Conserva los periodos cuyo pronostico es cero y su demanda es positiva")
    void noExcluyeUnPronosticoCero() {
        assertEquals(100.0, calculadora.mape(List.of(25.0), List.of(0.0)), TOLERANCIA);
    }

    @Test
    @DisplayName("Rechaza series de distinta longitud al calcular MAPE")
    void rechazaSeriesDeDistintaLongitud() {
        assertThrows(IllegalArgumentException.class,
                () -> calculadora.mape(List.of(10.0, 20.0), List.of(10.0)));
    }

    @Test
    @DisplayName("Rechaza series vacias al calcular MAPE")
    void rechazaSeriesVacias() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.mape(List.of(), List.of()));
    }
}
