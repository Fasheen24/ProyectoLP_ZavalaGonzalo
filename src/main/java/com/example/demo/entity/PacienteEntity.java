package com.example.demo.entity;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_paciente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PacienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "correo")
    private String correo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "celular")
    private String celular;

    @Column(name = "dni")
    private String dni; 

    @Column(name = "nro_historia", unique = true)
    private Integer nroHistoria; 
    
 // Variable estática para generación de números de historia
    private static final AtomicInteger nroHistoriaGenerator = new AtomicInteger(1000);

    // Getters y Setters

    @PrePersist
    public void prePersist() {
        if (this.nroHistoria == null) {
            this.nroHistoria = nroHistoriaGenerator.incrementAndGet();
        }
    }
}
