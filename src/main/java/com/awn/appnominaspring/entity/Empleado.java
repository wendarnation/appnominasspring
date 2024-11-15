package com.awn.appnominaspring.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "empleados")
@Data // Lombok genera getters, setters, toString, equals, y hashCode
@NoArgsConstructor // Lombok genera un constructor vacío
@AllArgsConstructor // Lombok genera un constructor con todos los campos
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Column(unique = true)
    private String dni;

    @NotNull(message = "El sexo es obligatorio")
    private char sexo;

    @Min(value = 1, message = "La categoría debe estar entre 1 y 10")
    @Max(value = 10, message = "La categoría debe estar entre 1 y 10")
    private int categoria = 1;

    @Min(value = 0, message = "Los años no pueden ser negativos")
    private int anyos = 0;

    // Constructor personalizado
    public Empleado(String nombre, String dni, char sexo, int categoria, int anyos) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
        this.setCategoria(categoria);
        this.setAnyos(anyos);
    }

    // Métodos adicionales
    public void incrAnyo() {
        this.anyos++;
    }

    public void imprime() {
        System.out.println(nombre + ", " + dni + ", " + sexo + ", " + categoria + ", " + anyos);
    }


}