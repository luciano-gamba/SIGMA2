package org.tallerjava.moduloMonitoreo.infraestructura;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;

@ApplicationScoped
public class RegistradorDeMetricas {

    public static final String a_cantidad_de_cargas_activas = "a_cantidad_de_cargas_activas";
    public static final String b_cantidad_de_cargas_realizadas = "b_cantidad_de_cargas_realizadas";
    public static final String c_cantidad_de_pagos_realizados_con_UTE = "c_cantidad_de_pagos_realizados_con_UTE";
    public static final String d_cantidad_de_pagos_realizados_con_Tarjetas = "d_cantidad_de_pagos_realizados_con_Tarjetas";
    public static final String e_ocurrió_un_error_al_pagar_con_Tarjeta = "e_ocurrió_un_error_al_pagar_con_Tarjeta";

    private InfluxConfig config;
    
    @PostConstruct
    public void init() {
        //configuración del repositorio de metricas (influxdb)
        config = new InfluxConfig() {
            @Override
            public String get(String s) {
                return null;
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(10);
            }

            @Override
            public String db() {
                return "metricasTallerJava";
            }
        };


    }

    public void incrementarCounter(String nombreCounter) {
        MeterRegistry meterRegistry;
        meterRegistry = new InfluxMeterRegistry(config, Clock.SYSTEM);
        meterRegistry.counter(nombreCounter).increment();
    }
}
