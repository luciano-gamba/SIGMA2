# **SIGMA2**

## Sistema de Gestión de Movilidad Eléctrica

## Integrantes

| Nombre |
| :---: |
| [Lucas Brito](mailto:lucas.brito@estudiantes.utec.edu.uy) |
| [Matthew Freire](mailto:matthew.freire@estudiantes.utec.edu.uy) |
| [Luciano Gamba](mailto:luciano.gamba@estudiantes.utec.edu.uy) |
| [Alan Machado](mailto:alan.machado@estudiantes.utec.edu.uy) |

En este taller desarrollamos el backend de servicios para una aplicación de sistema de gestión de movilidad eléctrica diseñado para gestionar la recarga de vehículos eléctricos.

     Decidimos usar una arquitectura de monolito modular, el cual consiste de la aplicación desplegada en una sola capa física (monolito) pero dividido en varios módulos distintos bien separados.   
     Esto tiene la ventaja de que combina la simplicidad de un monolito, con la organización y desacoplamiento de los microservicios.

## **Modelo de dominio**

<img width="1737" height="741" alt="image" src="https://github.com/user-attachments/assets/94ee9652-36fe-40be-900f-83c5901214af" />


## **Módulo Cargas**

Eventos escuchados (**/interfase/evento/in**):

Mediante el **ObserverModuloCliente.java** el moduloCargas consume eventos **ClienteNuevoCliente** publicados por el ModuloCliente para insertar en sus tablas de clientes, a estos nuevos Clientes.   
A su vez también utilizando el **ObserverModuloCliente.java** el moduloCargas consume los eventos **ClienteNuevaTarjeta** y eventos **ClienteNuevaCuentaUTE** para poder insertar en su tabla de medio de pago a estos nuevos medios de pagos.

Mediante el **ObserverModuloPagos.java** este modulo consume tanto eventos **EventoTarjeta** y **EventoCuentaUTE** para poder confirmar el pago de una **Carga** manteniendo así la consistencia de los Pagos.

Eventos publicados (**/interfase/evento/on**):

Mediante el **PublicadorEventoCarga.java** se publican las Cargas a pagar delegando esa parte al **ModuloPagos**.

Endpoints expuestos (**/interfase/remota/rest**):  
Mediante **CargasAPI** se exponen los siguientes endpoints:

* iniciarCarga  
  * [http://localhost:8080/SIGMA2/moduloCargas/carga/iniciar](http://localhost:8080/SIGMA2/moduloCargas/carga/iniciar)  
* obtenerCargaActual  
  * [http://localhost:8080/SIGMA2/moduloCargas/get/cargaActual/{cedula}](http://localhost:8080/SIGMA2/moduloCargas/get/cargaActual/{cedula)  
* obtenerHistorialDeCargas  
  * [http://localhost:8080/SIGMA2/moduloCargas/get/historico](http://localhost:8080/SIGMA2/moduloCargas/get/historico)  
* obtenerEstaciones  
  * [http://localhost:8080/SIGMA2/moduloCargas/get/estaciones](http://localhost:8080/SIGMA2/moduloCargas/get/estaciones)  
* finalizarCarga (se llama desde el Cargador \- hardware )  
  * [http://localhost:8080/SIGMA2/moduloCargas/carga/finalizar](http://localhost:8080/SIGMA2/moduloCargas/carga/finalizar)  
* altaEstacion  
  * [http://localhost:8080/SIGMA2/moduloCargas/alta/estacion](http://localhost:8080/SIGMA2/moduloCargas/alta/estacion)  
* altaCargador  
  * [http://localhost:8080/SIGMA2/moduloCargas/alta/cargador](http://localhost:8080/SIGMA2/moduloCargas/alta/cargador)  
* reintentarPagoCarga  
  * [http://localhost:8080/SIGMA2/moduloCargas/carga/reintentar/{cedula}](http://localhost:8080/SIGMA2/moduloCargas/carga/reintentar/{cedula)


## **Módulo Clientes**

Eventos escuchados (**/interfase/in**):

- No escucha ningún evento.

Eventos publicados (**/interfase/evento/out**):

Mediante el **PublicadorEventoCliente** se publica:

- publicarNuevoCliente  
- publicarNuevaTarjeta  
- publicarNuevaCuentaUTE

Endpoints expuestos (**/interfase/remota/rest**):

Mediante ClientesAPI se exponen los siguientes endpoints:

- http://localhost:8080/SIGMA2/moduloCliente (registrar Cliente)  
- [http://localhost:8080/SIGMA2/moduloCliente/getClientes](http://localhost:8080/SIGMA2/moduloCliente/getClientes) (getClientes)  
- [http://localhost:8080/SIGMA2/moduloCliente/iniciarSesion](http://localhost:8080/SIGMA2/moduloCliente/iniciarSesion) (iniciarSesion)  
- [http://localhost:8080/SIGMA2/moduloCliente/realizarReclamo](http://localhost:8080/SIGMA2/moduloCliente/realizarReclamo) (registrarReclamo)


Mediante MedioPagoAPI se exponen los siguientes endpoints:

- [http://localhost:8080/SIGMA2/MedioPago](http://localhost:8080/SIGMA2/MedioPago) (registrarMedioPago)

## **Módulo Pagos**

Eventos escuchados (**/interfase/in**):

1\)

* Mediante el **ObservadorEventoNuevoCliente.java** consume el evento **ClienteNuevoCliente.java** del MODULO CLIENTES.

* Mediante el **ObservadorEventoNuevaCuentaUTE.java** consume el evento **ClienteNuevaCuentaUTE.java** del MODULO CLIENTES.  
* Mediante el **ObservadorEventoNuevaTarjeta.java** consume el evento **ClienteNuevaTarjeta.java** del MODULO CLIENTES.

2\)

* Mediante el **ObservadorEventoCargaAPagar.java** consume el evento **CargaAPagar.java** del MODULO CARGA.


Eventos publicados (**/interfase/out**): 

3\)

* Mediante el **PublicadorEventoPago.java** se publica el evento **EventoCuentaUTE.java** y   
  **EventoTarjeta.java** 

Comportamiento:

1) Luego de cada inserción de clientes o medios de pago (*MÓDULO CLIENTES*) la información es guardada en la propia persistencia.  
2) Luego de finalizada una carga (*MÓDULO CARGAS*) la información es procesada y almacenada en la propia persistencia.  
3) La información del pago (*resultado de la transacción*) es publicada para ser consumida por el MÓDULO CARGAS.

<img width="1152" height="550" alt="image" src="https://github.com/user-attachments/assets/5142d523-aead-4777-a456-fb32c83f2651" />

