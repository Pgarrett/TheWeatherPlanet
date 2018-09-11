# TheWeatherPlanet


## Configuración
La aplicación se ejecuta con el comando "java -jar TheWeatherPlanet.jar". Es necesaria la presencia de un archivo de configuración, ubicado en el directorio "./config/", cuyo nombre sea "theweatherplanet.cfg". El mismo tiene el siguiente formato:

    {
        "years": 10,
        "data-directory": "data",
        "data-file": "data.json"
    }

La definición de los parámetros es la siguiente:
* "years": número natural que indica la cantidad de años a pronosticar.
* "data-directory": directorio donde se escribirá el archivo de salida con el pronóstico.
* "data-file": nombre del archivo de salida que contendrá el pronóstico. Debe ser extensión ".json".

En el mismo directorio, debe encontrarse también el archivo "log4j.properties" que contiene la información necesaria para el log de la aplicación.

    log4j.rootLogger=TRACE, file, console

    log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
    log4j.appender.file.File=./log/theWeatherPlanet
    log4j.appender.file.DatePattern='_'yyyyMMdd'.log'
    log4j.appender.file.Append=true
    log4j.appender.file.layout=org.apache.log4j.PatternLayout
    log4j.appender.file.layout.ConversionPattern =%d{ABSOLUTE} | %t | %5p %c{1}:%L | %m%n

    log4j.appender.console=org.apache.log4j.ConsoleAppender
    log4j.appender.console.Target=System.out
    log4j.appender.console.layout=org.apache.log4j.PatternLayout
    log4j.appender.console.layout.ConversionPattern=%d{ABSOLUTE} | %t | %5p %c{1}:%L | %m%n


## Ejecución

La aplicacicón no contempla años bisiestos, por lo tanto la cantidad de días a pronosticar es igual a la cantidad de años ingresados por configuración , multiplicado por 365. El pronóstico inicia en el día 0 y finaliza en el día: (añosAPronosticar * 365) - 1.

La posición de cada planeta puede ser expresada de dos formas:
* En grados, sea p la posición del planeta, luego 0 <= p < 360.
* En coordenadas (x, y) sobre ejes cartesianos.