# XData PF - Java API

En este repositorio se encuentran todos los archivos correspondientes a la API realizada en Java para servir las diferentes consultas utilizando el índice generado en ElasticSearch.

Como ejecutar la API:

Dentro de la carpeta principal del repositorio se encuentra el archivo `run.sh`. Simplemente ejecutar el siguiente comando:

	./run.sh &

Dicho comando levanta la API en caso de no estar previamente levantada y en caso contrario muestra un mensaje avisando que ya se encontraba corriendo.
A continuación, en caso de que no estuviese corriendo previamente, ejecutar el comando:

	disown

El cual desliga la ejecución de la API del usuario que la levanto, de esta forma la misma quedará corriendo aunque dicho usuario cierre sesión.

