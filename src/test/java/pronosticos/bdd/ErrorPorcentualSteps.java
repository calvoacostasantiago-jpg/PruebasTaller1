package pronosticos.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dadas;
import io.cucumber.java.es.Entonces;

import pronosticos.CalculadoraPronosticos;

/** Regla de negocio sobre periodos sin demanda, expresada con una tabla de datos. */
public class ErrorPorcentualSteps {

    private static final double TOLERANCIA = 0.01;
    private final CalculadoraPronosticos calculadora = new CalculadoraPronosticos();
    private List<Double> reales;
    private List<Double> pronosticos;
    private double desviacion;

    @Dadas("las demandas reales y los pronósticos de los periodos")
    public void lasDemandasYLosPronosticos(DataTable tabla) {
        List<Map<String, String>> periodos = tabla.asMaps(String.class, String.class);
        reales = periodos.stream().map(periodo -> Double.valueOf(periodo.get("real"))).toList();
        pronosticos = periodos.stream().map(periodo -> Double.valueOf(periodo.get("pronostico"))).toList();
    }

    @Cuando("evalúo la desviación porcentual del plan de producción")
    public void evaluoLaDesviacionPorcentual() {
        desviacion = calculadora.mape(reales, pronosticos);
    }

    @Entonces("la desviación porcentual media es {double} %")
    public void laDesviacionPorcentualMediaEs(double esperado) {
        assertEquals(esperado, desviacion, TOLERANCIA);
    }
}
