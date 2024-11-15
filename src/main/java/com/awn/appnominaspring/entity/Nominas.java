package com.awn.appnominaspring.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "nominas")
@Data
public class Nominas {

    @Id
    @NotNull
    @Column(name = "dni", insertable = false, updatable = false)
    private String dni;

    @NotNull
    private Integer sueldo;

    @OneToOne
    @JoinColumn(name = "dni", referencedColumnName = "dni")
    private Empleado empleado;

    public Nominas() {
    }

    public Nominas(String dni, Integer sueldo) {
        this.dni = dni;
        this.sueldo = sueldo;
    }

}