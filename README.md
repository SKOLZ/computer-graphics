# Trabajo Practico Computacion Grafica
## Raytracer

### Build

1) correr en la consola parado en el directorio del proyecto:

```
mvn clean package
```

2) entrar a la carpeta target y ejecutar el siguiente comando utilizando los parametros mencionados a continuacion:

```
java -jar raytracer.jar <PARAMETROS>
```

#### Parametros

##### [OBLIGATORIOS]

###### -i [nombre del archivo de entrada]
###### -aa [valor entero de cantidad de muestras de antialiasing]

##### [OPCIONALES]

###### -o [nombre del archivo de salida]
de no usarse se le pondra el nombre del archivo de entreda con extension .png

###### -time
no recibe parametro a continuacion y hace que se muestre el tiempo que tarda en realizar la imagen.

###### -d [numero entero que representa la profundidad de rayos reflejados]

###### -benchmark [numero entero que representa la cantidad de veces  correr el render>]

