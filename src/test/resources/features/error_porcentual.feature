# language: es
Característica: Medición porcentual en periodos con demanda cero
  Como planeador de producción
  Quiero medir la desviación solamente en los periodos con demanda positiva
  Para no distorsionar el porcentaje con periodos sin demanda

  Escenario: Un periodo sin demanda no cuenta en el promedio porcentual
    Dadas las demandas reales y los pronósticos de los periodos
      | real | pronostico |
      | 80   | 80         |
      | 0    | 0          |
      | 95   | 100        |
    Cuando evalúo la desviación porcentual del plan de producción
    Entonces la desviación porcentual media es 2,63 %
