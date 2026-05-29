package org.tallerjava.moduloCargas.infraestructura.ratelimiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

import java.time.Duration;

@ApplicationScoped
public class RateLimiter {
    private Bucket bucket;
    @Getter
    private boolean activo;

    @PostConstruct
    public void inicializar() {
        activo = true;
        // cada vez que llega un request, se quita un elemento del balde
        // si el balde se queda vacío los request serán rechazados
        Bandwidth bucketConf = Bandwidth.builder() //config del balde
                .capacity(10) //capacidad inicial
                .refillGreedy(5, Duration.ofSeconds(1))
        // refillGreedy(cuantos tokens intentará llenar, en qué lapso de tiempo) intentando distribuir el llenado de forma regular
        // ej: refillGreedy(5, Duration.ofSeconds(1)) = token se creara cada 200 milisegundos (1/5=0,200)
        // otra opción = .refillIntervally(5, Duration.ofSeconds(1)) // en lugar de distribuir el relleno, lo hace de golpe
                .build();

        bucket = Bucket.builder().addLimit(bucketConf).build();
    }

    public boolean  consumir() {
        boolean result = bucket.tryConsume(1);
        System.out.println("Tokens restantes: " + bucket.getAvailableTokens());
        return result;
    }

    // no veo necesario el poder activar/desactivar el rateLimiter, por lo que lo dejo comentado por las dudas
//    public void activarRateLimiter(boolean estado) {
//        System.out.println("RateLimiter estado: " + estado);
//        this.activo = estado;
//    }

}
