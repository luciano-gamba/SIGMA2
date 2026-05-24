use tallerJava;

insert into moduloCarga_ESTACION_CARGA (descripcion,calle,departamento,longitud,latitud) values ('Estación de Carga de Parque del Plata','Calle 9 e Interbalnearia','Canelones',1330,9160);
insert into moduloCarga_ESTACION_CARGA (descripcion,calle,departamento,longitud,latitud) values ('Estación de Carga de ANCAP','Alvariza y 18 de julio','Maldonado',1330,9160);

insert into moduloCarga_CARGADOR (tipoCargador,tieneCable,tipoConector,estadoCargador,costePorHora,estacion_id,potenciaMinima) values (1, false, 4, 0,150, 1,30);
insert into moduloCarga_CARGADOR (tipoCargador,tieneCable,tipoConector,estadoCargador,costePorHora,estacion_id,potenciaMinima) values (2, true, 2, 0,50, 1,0);
insert into moduloCarga_CARGADOR (tipoCargador,tieneCable,tipoConector,estadoCargador,costePorHora,estacion_id,potenciaMinima) values (1, false, 3,0 ,100,2,25);
insert into moduloCarga_CARGADOR (tipoCargador,tieneCable,tipoConector,estadoCargador,costePorHora,estacion_id,potenciaMinima) values (1,true,1,0,180,2,50);

insert into moduloClientes_CLIENTE (cedula, nombreCompleto, telefono, contrasenia) values ('55326750' ,'Alan Nahuel Machado Sosa','094755370','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
insert into moduloClientes_CLIENTE (cedula, nombreCompleto, telefono, contrasenia) values ('56422315','Lucas Darío Brito Recuero','091793726','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
insert into moduloClientes_CLIENTE (cedula, nombreCompleto, telefono, contrasenia) values ('59924162','Matthew Freire','092095897','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
insert into moduloClientes_CLIENTE (cedula, nombreCompleto, telefono, contrasenia) values ('55418575','Luciano Gamba','099897976','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');

insert into moduloClientes_CLIENTE_COMUN (cedula) values ('55326750');
insert into moduloClientes_CLIENTE_COMUN (cedula) values ('56422315');
insert into moduloClientes_CLIENTE_PROFESIONAL (cedula, porcentajeDescuento, tipo) values ('59924162',35, 1);
insert into moduloClientes_CLIENTE_PROFESIONAL (cedula, porcentajeDescuento, tipo) values ('55418575',20, 3);

insert into moduloCarga_Cliente (cedula, nombreCompleto, telefono, contrasenia, descuento) values ('55326750' ,'Alan Nahuel Machado Sosa','094755370','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',0);
insert into moduloCarga_Cliente (cedula, nombreCompleto, telefono, contrasenia, descuento) values ('56422315','Lucas Darío Brito Recuero','091793726','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',0);
insert into moduloCarga_Cliente (cedula, nombreCompleto, telefono, contrasenia, descuento) values ('59924162','Matthew Freire','092095897','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',35);
insert into moduloCarga_Cliente (cedula, nombreCompleto, telefono, contrasenia, descuento) values ('55418575','Luciano Gamba','099897976','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',20);

insert into moduloPagos_Cliente (cedula, nombreCompleto) values ('55326750' ,'Alan Nahuel Machado Sosa');
insert into moduloPagos_Cliente (cedula, nombreCompleto) values ('56422315','Lucas Darío Brito Recuero');
insert into moduloPagos_Cliente (cedula, nombreCompleto) values ('59924162','Matthew Freire');
insert into moduloPagos_Cliente (cedula, nombreCompleto) values ('55418575','Luciano Gamba');
    