package pronosticos.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import pronosticos.CalculadoraPronosticos;

class AlertasTest {

    private final CalculadoraPronosticos calculadora = new CalculadoraPronosticos();

    @Test
    @DisplayName("Genera alerta cuando el error supera el umbral")
    void alertaCuandoSuperaElUmbral() {
        assertTrue(calculadora.debeGenerarAlerta(12.0, 10.0));
    }

    @Test
    @DisplayName("Genera alerta cuando el error es igual al umbral")
    void alertaCuandoIgualaElUmbral() {
        assertTrue(calculadora.debeGenerarAlerta(10.0, 10.0));
    }

    @Test
    @DisplayName("No genera alerta cuando el error queda bajo el umbral")
    void noAlertaPorDebajoDelUmbral() {
        assertFalse(calculadora.debeGenerarAlerta(7.0, 10.0));
    }

    @ParameterizedTest(name = "umbral = {0}")
    @ValueSource(doubles = {-1.0, 150.0})
    @DisplayName("Rechaza umbrales fuera del intervalo de cero a cien")
    void rechazaUmbralesFueraDeRango(double umbral) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.debeGenerarAlerta(12.0, umbral));

        assertEquals("El umbral debe estar entre 0 y 100", error.getMessage());
    }

    @ParameterizedTest(name = "umbral limite = {0}")
    @ValueSource(doubles = {0.0, 100.0})
    @DisplayName("Admite los dos extremos del intervalo del umbral")
    void admiteLosExtremosDelUmbral(double umbral) {
        assertTrue(calculadora.debeGenerarAlerta(umbral, umbral));
    }
}
