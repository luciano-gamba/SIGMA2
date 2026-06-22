<h1 align="center"> SIGMA2</h1>

<h2 align="center">Sistema de Gestión de Movilidad Eléctrica</h2>

<h2 align="center"> Integrantes</h2>
<table align="center">
  <tr>
    <th>Nombre</th>
  </tr>
  <tr>
    <td><a href="mailto:lucas.brito@estudiantes.utec.edu.uy">Lucas Brito</a></td>
  </tr>
  <tr>
    <td><a href="mailto:matthew.freire@estudiantes.utec.edu.uy">Matthew Freire</a></td>
  </tr>
  <tr>
    <td><a href="mailto:luciano.gamba@estudiantes.utec.edu.uy">Luciano Gamba</a></td>
  </tr>
  <tr>
    <td><a href="mailto:alan.machado@estudiantes.utec.edu.uy">Alan Machado</a></td>
  </tr>
</table>
En este taller desarrollamos el backend de servicios para una aplicación de sistema de gestión de movilidad eléctrica diseñado para gestionar la recarga de vehículos eléctricos.

     Decidimos usar una arquitectura de monolito modular, el cual consiste de la aplicación desplegada en una sola capa física (monolito) pero dividido en varios módulos distintos bien separados.   
     Esto tiene la ventaja de que combina la simplicidad de un monolito, con la organización y desacoplamiento de los microservicios.

## Modelo de dominio

<img width="1737" height="741" alt="image" src="https://github.com/user-attachments/assets/94ee9652-36fe-40be-900f-83c5901214af" />

## **Módulo Cargas**

### Eventos escuchados (**/interfase/evento/in**):

Mediante el **ObserverModuloCliente.java** el moduloCargas consume eventos **ClienteNuevoCliente** publicados por el ModuloCliente para insertar en sus tablas de clientes, a estos nuevos Clientes.   
A su vez también utilizando el **ObserverModuloCliente.java** el moduloCargas consume los eventos **ClienteNuevaTarjeta** y eventos **ClienteNuevaCuentaUTE** para poder insertar en su tabla de medio de pago a estos nuevos medios de pagos.

Mediante el **ObserverModuloPagos.java** este modulo consume tanto eventos **EventoTarjeta** y **EventoCuentaUTE** para poder confirmar el pago de una **Carga** manteniendo así la consistencia de los Pagos.

### Eventos publicados (**/interfase/evento/on**):

Mediante el **PublicadorEventoCarga** se publican eventos tipo:

- **CargaAPagar**  
  - Al finalizar una carga o al reintentar un pago  
- **EventoCargaActiva**  
  - Al iniciar una carga nueva

### Endpoints expuestos (**/interfase/remota/rest**):

Mediante **CargasAPI** se exponen los siguientes endpoints:

* iniciarCarga  
  * [http://localhost:8080/SIGMA2/moduloCargas/carga/iniciar](http://localhost:8080/SIGMA2/moduloCargas/carga/iniciar)  
  * body \= {“idCargador”: int, 

     "cedula": "string", 

     "idMedioPago": int}

* obtenerCargaActual  
  * [http://localhost:8080/SIGMA2/moduloCargas/get/cargaActual/{**cedula**}](http://localhost:8080/SIGMA2/moduloCargas/get/cargaActual/{cedula)  
* obtenerHistorialDeCargas  
  * [http://localhost:8080/SIGMA2/moduloCargas/get/historico](http://localhost:8080/SIGMA2/moduloCargas/get/historico)  
  * body \= {"cedula": "string", 

     "inicio": "LocalDateTime", 

     "fin": "LocalDateTime"}

		(LocalDateTime format: aaaa-mm-ddThh:mm:ss)

* obtenerEstaciones  
  * [http://localhost:8080/SIGMA2/moduloCargas/get/estaciones](http://localhost:8080/SIGMA2/moduloCargas/get/estaciones)  
* finalizarCarga (se llama desde el Cargador \- hardware )  
  * [http://localhost:8080/SIGMA2/moduloCargas/carga/finalizar](http://localhost:8080/SIGMA2/moduloCargas/carga/finalizar)  
  * body \= {"idCargador": int, 

     "tiempoRecargo": int}

* altaEstacion  
  * [http://localhost:8080/SIGMA2/moduloCargas/alta/estacion](http://localhost:8080/SIGMA2/moduloCargas/alta/estacion)  
  * body \= {"descripcion": "string",

     "calle": "string",

     "departamento": "string",

     "longitud": int,

     "latitud": int}

* altaCargador  
  * [http://localhost:8080/SIGMA2/moduloCargas/alta/cargador](http://localhost:8080/SIGMA2/moduloCargas/alta/cargador)  
  * body \= {"tipoCargador": int,

     "tieneCable": bool,

     "tipoConector": int,

     "costePorHora": double,

     "miEstacionCarga": int}

* reintentarPagoCarga  
  * [http://localhost:8080/SIGMA2/moduloCargas/carga/reintentar/{**cedula**}](http://localhost:8080/SIGMA2/moduloCargas/carga/reintentar/{cedula)

Debido al consumo de recursos que genera consultar el histórico de cargas, este quedó protegida por un rate limiter, el cual tiene por defecto una implementación refillGreedy con capacidad de 20 tokens, rellenando de forma distribuida 10 tokens cada segundo.  
<img width="926" height="305" alt="image4" src="https://github.com/user-attachments/assets/87a20217-1e32-45ad-b996-7c0e71e75acf" />
<p align="center">Test en JMeter</p>
<img width="537" height="418" alt="image5" src="https://github.com/user-attachments/assets/ec075f8e-cbe9-4c6d-8ad3-e8cf2c32bd81" />
<img width="612" height="294" alt="image1" src="https://github.com/user-attachments/assets/a809937f-8e3d-43d5-8ab3-effa5bdf8d24" />



## **Módulo Clientes**

### Eventos escuchados (**/interfase/in**):

- No escucha ningún evento.

### Eventos publicados (**/interfase/evento/out**):

Mediante el **PublicadorEventoCliente** se publica:

- publicarNuevoCliente  
- publicarNuevaTarjeta  
- publicarNuevaCuentaUTE

### Endpoints expuestos (**/interfase/remota/rest**):

Mediante ClientesAPI se exponen los siguientes endpoints:

- [http://localhost:8080/SIGMA2/moduloCliente](http://localhost:8080/SIGMA2/moduloCliente) (registrar Cliente)  
- body \= {"cedula": "string",  
  "nombreCompleto": "string",  
  "telefono": “string”,  
  "contrasenia": ”string”,  
  "mediosDePago":\[\]  
  //si es profesional//  
  "porcentajeDescuento": double,  
  "tipo": “EnumTipoProfesional(opciones: TAXI,UBER,CABIFY)”  
  }  
- [http://localhost:8080/SIGMA2/moduloCliente/getClientes](http://localhost:8080/SIGMA2/moduloCliente/getClientes) (getClientes)  
- [http://localhost:8080/SIGMA2/moduloCliente/iniciarSesion](http://localhost:8080/SIGMA2/moduloCliente/iniciarSesion) (iniciarSesion)  
- body \= {"cedula":"string",

  "contrasenia":"string"

  }

- [http://localhost:8080/SIGMA2/moduloCliente/realizarReclamo](http://localhost:8080/SIGMA2/moduloCliente/realizarReclamo) (registrarReclamo)  
- body \= {"cliente":

  {"cedula":"string"}, 

  "comentario":"string"

  }

Mediante MedioPagoAPI se exponen los siguientes endpoints:

- [http://localhost:8080/SIGMA2/MedioPago](http://localhost:8080/SIGMA2/MedioPago) (registrarMedioPago)  
  - Este endpoint requiere que se le envíen las credenciales del cliente para funcionar, tanto la cédula como la contraseña. Para enviarle las credenciales se debe agregar \-u “\[ci cliente\] : \[contraseña\]". La cédula de las credenciales y el del body deben coincidir o devolverá error.  
  - body \= {

     "cliente":

    {"cedula": "string"},

    "medioPago":

    {"numeroCuenta": "string"} // Cuenta UTE

    {“numero”: “string”,

     “fechaVencimiento”: “LocalDate”,

     “digitoVerificador”: “string”} // Tarjeta

    }

## **Módulo Pagos**

### Eventos escuchados (**/interfase/in**):

1\)

* Mediante el **ObservadorEventoNuevoCliente.java** consume el evento **ClienteNuevoCliente.java** del MODULO CLIENTES.

* Mediante el **ObservadorEventoNuevaCuentaUTE.java** consume el evento **ClienteNuevaCuentaUTE.java** del MODULO CLIENTES.  
* Mediante el **ObservadorEventoNuevaTarjeta.java** consume el evento **ClienteNuevaTarjeta.java** del MODULO CLIENTES.

2\)

* Mediante el **ObservadorEventoCargaAPagar.java** consume el evento **CargaAPagar.java** del MODULO CARGA.


### Eventos publicados (**/interfase/out**): 

3\)

* Mediante el **PublicadorEventoPago.java** se publica el evento **EventoCuentaUTE.java** y   
  **EventoTarjeta.java** 

### Endpoints de la API (**/interfase/out/API**):

4\)

* Mediante **PagosAPI.java** se exponen el endpoint:  
    
  **GET** [http://localhost:8080/SIGMA2/moduloPagos/pagos](http://localhost:8080/SIGMA2/moduloPagos/pagos)   
    
  teniendo en el body un JSON con los siguientes campos:  
  {  
  	"cedula" : "string",  
  	"inicio" : "LocalDate",  
  	"fin" : "LocalDate"  
  (LocalDate format: aaaa-mm-dd)  
  } 

### Comportamiento:

1) Luego de cada inserción de clientes o medios de pago (*MÓDULO CLIENTES*) la información es guardada en la propia persistencia.  
2) Luego de finalizada una carga (*MÓDULO CARGAS*) la información es procesada y almacenada en la propia persistencia.  
3) La información del pago (*resultado de la transacción*) es publicada para ser consumida por el MÓDULO CARGAS.  
4) A la hora de consultar los pagos de un usuario particular, filtrados por un intervalo de fechas, se recurre a dicho endpoint.

<img width="1152" height="550" alt="image" src="https://github.com/user-attachments/assets/5142d523-aead-4777-a456-fb32c83f2651" />

### Para agregar el SistemaExternoPAGOS.war manualmente : 

1. Desplegar el servidor en SIGMA2  
2. Copiar el .war  
3. Pegar el .war en SIGMA2/target/server/standalone/deployments

### Comandos (en la carpeta raíz):

	\> mvn clean package wildfly:dev  
	\> \[Ctrl+Shift+T\]  
	\> cp SistemaExternoPAGOS.war /target/server/standalone/deployments

### Comportamiento del SistemaExternoPAGOS:

* Los pagos con cuantaUTE siempre serán aceptados y guardados.  
* Los pagos con Tarjeta:  
  * Con importe igual a 0, darán error.  
  * Con número (de tarjeta) igual a 0, darán error.  
  * Aleatoriamente (1/5) darán error.

## **Módulo Monitoreo**

Para medir las métricas de ciertas acciones sucedidas en la aplicación se envían eventos el cuál este módulo escucha para incrementar los contadores de dichas acciones.

Para registrar esas métricas usamos la dependencia **Micrometer**, el cual que se comunica con la base de datos **InfluxDB** para que luego **Grafana** tome esos datos para mostrarlos en gráficas, estos dos últimos los corremos dentro de un contenedor **Docker**, más específicamente usando la imagen de Docker “[philhawthorne/docker-influxdb-grafana](https://hub.docker.com/r/philhawthorne/docker-influxdb-grafana/)” el cuál ya incluye configurado InfluxDB y Grafana.  

<img width="379" height="85" alt="image8" src="https://github.com/user-attachments/assets/0e971a9d-d23b-4eb0-8495-78ebd7927326" /> 
<img width="911" height="154" alt="image6" src="https://github.com/user-attachments/assets/1a4609b9-082f-4683-8070-d8430ea35408" />
<img width="495" height="104" alt="image7" src="https://github.com/user-attachments/assets/562a1283-c7ff-42b7-934a-8c05caceed54" />  
RegistradorDeMetricas

### Métricas que mide

* Cantidad de cargas activas

* Cantidad de cargas realizadas

* Cantidad de pagos realizados con cuenta UTE

* Cantidad de pagos realizados con Tarjetas

* Cuando ocurrió un error al pagar con Tarjeta

### 

### Eventos escuchados (**/interfase/evento/in**)

Mediante el **ObserverModuloCargas** se escuchan eventos tipo:

- **EventoCargaActiva**  
  - Aumenta contador “**a\_cantidad\_de\_cargas\_activas**”  
- **CargaAPagar**  
  - Aumenta contador “**b\_cantidad\_de\_cargas\_realizadas**”

Mediante el **ObserverModuloPagos** se escuchan eventos tipo:

- **EventoCuentaUTE**  
  - Aumenta contador “**c\_cantidad\_de\_pagos\_realizados\_con\_UTE**”  
- **EventoTarjeta**  
  - Si se aprobó el pago, aumenta contador “**d\_cantidad\_de\_pagos\_realizados\_con\_Tarjetas**”  
  - Si no se aprobó el pago, aumenta contador “**e\_ocurrió\_un\_error\_al\_pagar\_con\_Tarjeta**”

### 

### Pasos para activar monitoreo

Si se desea tener este monitoreo es necesario tener instalado Docker Engine en el sistema ([Descarga en Ubuntu](https://docs.docker.com/engine/install/ubuntu/)), luego seguir estos pasos:

* Descargar y correr imagen por primera vez  
  * **sudo docker run \-d \\**

    **\--name docker-influxdb-grafana \\**

    **\-p 3003:3003 \\**

    **\-p 3004:8083 \\**

    **\-p 8086:8086 \\**

    **\-v /path/for/influxdb:/var/lib/influxdb \\**

    **\-v /path/for/grafana:/var/lib/grafana \\**

    **philhawthorne/docker-influxdb-grafana:latest**

Con el contenedor levantado se puede acceder a la base de datos como se indicó antes o también a los gráficos de Grafana.

* Entrar a [http://localhost:3003](http://localhost:3003) (Grafana)

* Ingresar con:  
  * Usuario: **root**  
  * Contraseña: **root**

* Primero se debe crear el data source de la base de datos, para que el dashboard sepa de donde debe agarrar la info.  
  * Entrar a **Configuración→Data Sources→Add data source→InfluxDB**, y crearlo como a continuación:

<img width="492" height="700" alt="image4" src="https://github.com/user-attachments/assets/c739695c-84ec-4e1c-ab25-deda9dd00c03" />

Lo más importante es que quede exactamente igual:

* Name: **SIGMA2InfluxDB**  
  * Los dashboards tomarán del data source con este nombre

* HTTP → URL: **http://localhost:8086**  
  * Lugar donde encuentra la base de datos

* InfluxDB Details → Database: **metricasSIGMA2**  
  * El data source tomará de esta base de datos

Cuando se llamen a los endpoints correspondientes a las métricas se creará la base de datos en InfluxDB si no existe, y creará (si no existen) e incrementará los contadores para que luego los tome Grafana y los muestre.

<img width="1851" height="938" alt="image10" src="https://github.com/user-attachments/assets/f38718d7-63fa-44a7-98ee-82dcbf51466c" />

### Comandos extras

* Subir el contenedor  
  * **sudo docker start docker-influxdb-grafana**

* Bajar el contenedor  
  * **sudo docker stop docker-influxdb-grafana**

* Mostrar contenedores activos y su info (incluyendo su id)  
  * **sudo docker stats**

* Abrir el CLI de un contenedor activo  
  * **sudo docker exec \-it {idContenedor} bash**

* Entrar a InfluxDB  
  * **influx \-host localhost \-port 8086**

* Comandos básicos de InfluxDB  
  * show databases  
  * use {nombreBD}  
  * show measurements (“tablas”)  
  * select \* from {tabla}

