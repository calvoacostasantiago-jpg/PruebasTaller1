package pronosticos.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import pronosticos.CalculadoraPronosticos;

/**
 * Pruebas unitarias de promedioMovil.
 *
 * <p>Cada prueba sigue el patrón Arrange-Act-Assert (preparar, ejecutar, verificar)
 * y revisa un solo comportamiento. Los valores esperados salen de un cálculo
 * independiente hecho a mano, nunca del propio código que se está probando.
 */
class PromedioMovilTest {

    private static final List<Double> SERIE = List.of(120.0, 132.0, 128.0, 141.0, 138.0, 150.0);
    private static final double TOLERANCIA = 0.01;

    private CalculadoraPronosticos calculadora;

    // @BeforeEach se ejecuta antes de cada prueba: cada una arranca con un objeto nuevo.
    @BeforeEach
    void prepararCalculadora() {
        calculadora = new CalculadoraPronosticos();
    }

    @Test
    @DisplayName("Pronostica con el promedio de los últimos periodos")
    void pronosticaConElPromedioDeLosUltimosPeriodos() {
        double resultado = calculadora.promedioMovil(SERIE, 3);

        // (141 + 138 + 150) / 3 = 143. El tercer argumento de assertEquals es la tolerancia.
        assertEquals(143.00, resultado, TOLERANCIA);
    }

    @Test
    @DisplayName("Con ventana igual a la serie usa todos los periodos")
    void conVentanaIgualALaSerieUsaTodosLosPeriodos() {
        assertEquals(110.00, calculadora.promedioMovil(List.of(100.0, 110.0, 120.0), 3), TOLERANCIA);
    }

    @Test
    @DisplayName("Con ventana de un periodo repite el último valor")
    void conVentanaDeUnPeriodoRepiteElUltimoValor() {
        assertEquals(150.00, calculadora.promedioMovil(SERIE, 1), TOLERANCIA);
    }

    @Test
    @DisplayName("Rechaza una ventana mayor que la serie")
    void rechazaUnaVentanaMayorQueLaSerie() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.promedioMovil(List.of(100.0, 110.0), 3));

        assertEquals("La serie tiene menos periodos que la ventana configurada", error.getMessage());
    }

    // @ParameterizedTest ejecuta la misma prueba una vez por cada valor de @ValueSource.
    @ParameterizedTest(name = "ventana = {0}")
    @ValueSource(ints = {0, -1})
    @DisplayName("Rechaza una ventana que no es positiva")
    void rechazaUnaVentanaQueNoEsPositiva(int ventana) {
        assertThrows(IllegalArgumentException.class, () -> calculadora.promedioMovil(SERIE, ventana));
    }

    @Test
    @DisplayName("Ignora un valor antiguo aunque sea mucho mayor que la ventana")
    void ignoraLosValoresFueraDeLaVentana() {
        double resultado = calculadora.promedioMovil(List.of(1000000.0, 10.0, 20.0, 30.0), 3);

        assertEquals(20.0, resultado, TOLERANCIA);
    }

    @Test
    @DisplayName("Conserva los decimales al promediar la ventana")
    void conservaLosDecimales() {
        assertEquals(3.9, calculadora.promedioMovil(List.of(2.1, 3.3, 4.5), 2), TOLERANCIA);
    }
}
