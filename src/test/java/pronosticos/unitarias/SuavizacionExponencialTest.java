package pronosticos.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import pronosticos.CalculadoraPronosticos;

class SuavizacionExponencialTest {

    private static final List<Double> SERIE = List.of(120.0, 132.0, 128.0, 141.0, 138.0, 150.0);
    private static final double TOLERANCIA = 0.01;

    @Test
    @DisplayName("Pronostica el siguiente periodo con alfa 0.3")
    void pronosticaElSiguientePeriodoConAlfa03() {
        double resultado = new CalculadoraPronosticos().suavizacionExponencial(SERIE, 0.3);

        assertEquals(137.5546, resultado, TOLERANCIA);
    }

    @Test
    @DisplayName("Con alfa cero conserva la primera demanda")
    void conAlfaCeroConservaLaPrimeraDemanda() {
        assertEquals(120.0, new CalculadoraPronosticos().suavizacionExponencial(SERIE, 0.0), TOLERANCIA);
    }

    @Test
    @DisplayName("Con alfa uno usa la ultima demanda")
    void conAlfaUnoUsaLaUltimaDemanda() {
        assertEquals(150.0, new CalculadoraPronosticos().suavizacionExponencial(SERIE, 1.0), TOLERANCIA);
    }

    @ParameterizedTest(name = "alfa = {0}")
    @ValueSource(doubles = {-0.1, 1.5, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY})
    @DisplayName("Rechaza un alfa fuera del intervalo de cero a uno")
    void rechazaUnAlfaFueraDelIntervalo(double alfa) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> new CalculadoraPronosticos().suavizacionExponencial(SERIE, alfa));

        assertEquals("Alfa debe estar entre 0 y 1", error.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Exige al menos una demanda para iniciar el pronostico")
    void rechazaUnaSerieSinPeriodos(List<Double> serie) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> new CalculadoraPronosticos().suavizacionExponencial(serie, 0.3));

        assertEquals("Se necesita al menos un periodo", error.getMessage());
    }
}
