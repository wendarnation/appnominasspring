package com.awn.appnominaspring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nominas")
@Data
@NoArgsConstructor
public class Nominas {

    private static final int[] SUELDO_BASE = {50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000, 230000};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Empleado empleado;

    private double sueldoCalculado;

    // Calcula el sueldo de un empleado en función de su categoría y años
    public void calcularSueldo() {
        int categoria = empleado.getCategoria();
        this.sueldoCalculado = SUELDO_BASE[categoria - 1] + 5000 * empleado.getAnyos();
    }

    // Constructor adicional para inicializar la nómina de un empleado específico
    public Nominas(Empleado empleado) {
        this.empleado = empleado;
        calcularSueldo();
    }
}
