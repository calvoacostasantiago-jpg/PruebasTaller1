# Informe de la actividad de pruebas de software

Autor: Santiago Acosta Calvo.

Repositorio de entrega: https://github.com/calvoacostasantiago-jpg/PruebasTaller1

## Casos propios definidos antes de escribir las pruebas

Los resultados se calcularon de forma independiente. Estos casos complementan los ejemplos del repositorio.

| Función | Entrada | Resultado esperado y cálculo | Riesgo que cubre |
|---|---|---|---|
| Promedio móvil | Serie `[1000000, 10, 20, 30]`, ventana `3` | `20`: `(10 + 20 + 30) / 3` | Incluir por error un dato antiguo situado fuera de la ventana. |
| Promedio móvil | Serie `[2.1, 3.3, 4.5]`, ventana `2` | `3.9`: `(3.3 + 4.5) / 2` | Truncar los decimales o seleccionar una ventana incorrecta. |
| MAD | Real `[10, 20]`, pronóstico `[12, 18]` | `2`: `(|10 - 12| + |20 - 18|) / 2` | Permitir que un exceso y un defecto se cancelen si se omite el valor absoluto. |

## Estado del informe

Documento en elaboración. Las evidencias se incorporarán después de ejecutar cada verificación; los resultados de GitHub Actions requieren acceso al repositorio de entrega.
