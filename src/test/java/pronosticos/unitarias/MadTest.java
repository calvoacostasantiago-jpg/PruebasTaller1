package pronosticos.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pronosticos.CalculadoraPronosticos;

/** Pruebas unitarias del error absoluto medio (MAD). */
class MadTest {

    private static final double TOLERANCIA = 0.01;

    private CalculadoraPronosticos calculadora;

    @BeforeEach
    void prepararCalculadora() {
        calculadora = new CalculadoraPronosticos();
    }

    @Test
    @DisplayName("Calcula el error absoluto medio")
    void calculaElErrorAbsolutoMedio() {
        double resultado = calculadora.mad(List.of(141.0, 138.0, 150.0), List.of(126.67, 133.67, 135.67));

        // (14.33 + 4.33 + 14.33) / 3 = 11.00, calculado de forma independiente.
        // Los decimales se comparan con tolerancia, nunca con igualdad exacta.
        assertEquals(11.00, resultado, TOLERANCIA);
    }

    @Test
    @DisplayName("Es cero cuando el pronóstico es exacto")
    void esCeroCuandoElPronosticoEsExacto() {
        assertEquals(0.0, calculadora.mad(List.of(100.0, 100.0), List.of(100.0, 100.0)), TOLERANCIA);
    }

    @Test
    @DisplayName("Rechaza series de distinta longitud")
    void rechazaSeriesDeDistintaLongitud() {
        assertThrows(IllegalArgumentException.class,
                () -> calculadora.mad(List.of(100.0, 110.0), List.of(100.0)));
    }

    @Test
    @DisplayName("Rechaza series vacías")
    void rechazaSeriesVacias() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.mad(List.of(), List.of()));
    }

    @Test
    @DisplayName("Un exceso y un defecto del pronostico no cancelan sus errores")
    void noCancelaErroresDeSignosOpuestos() {
        double resultado = calculadora.mad(List.of(10.0, 20.0), List.of(12.0, 18.0));

        // (|-2| + |2|) / 2 = 2, aunque el promedio de los errores con signo sea cero.
        assertEquals(2.0, resultado, TOLERANCIA);
    }
}
