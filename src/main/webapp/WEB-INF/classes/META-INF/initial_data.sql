use tallerJava;

insert into moduloCarga_ESTACION_CARGA (descripcion,calle,departamento,longitud,latitud) values ('Estación de Carga de Parque del Plata','Calle 9 e Interbalnearia','Canelones',1330,9160);
insert into moduloCarga_ESTACION_CARGA (descripcion,calle,departamento,longitud,latitud) values ('Estación de Carga de ANCAP','Alvariza y 18 de julio','Maldonado',1330,9160);

insert into moduloCarga_CARGADOR (tipoCargador,tieneCable,tipoConector,estadoCargador,costePorHora,estacion_id,potenciaMinima) values (1, false, 4, 0,150, 1,30);
insert into moduloCarga_CARGADOR (tipoCargador,tieneCable,tipoConector,estadoCargador,costePorHora,estacion_id,potenciaMinima) values (2, true, 2, 0,50, 1,0);
insert into moduloCarga_CARGADOR (tipoCargador,tieneCable,tipoConector,estadoCargador,costePorHora,estacion_id,potenciaMinima) values (1, false, 3,0 ,100,2,25);
insert into moduloCarga_CARGADOR (tipoCargador,tieneCable,tipoConector,estadoCargador,costePorHora,estacion_id,potenciaMinima) values (1,true,1,0,180,2,50);

