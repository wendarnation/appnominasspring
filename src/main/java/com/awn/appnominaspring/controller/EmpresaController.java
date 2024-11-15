package com.awn.appnominaspring.controller;

import com.awn.appnominaspring.entity.Empleado;
import com.awn.appnominaspring.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/inicio")
    public String inicio() {
        return "/inicio"; // Ruta de la vista de inicio
    }

    @GetMapping("/mostrarEmpleados")
    public String mostrarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
        model.addAttribute("empleados", empleados);
        return "empresa/mostrarEmpleados"; // Vista que muestra la lista de empleados
    }

    @GetMapping("/buscarSalario")
    public String buscarSalario() {
        return "empresa/buscarSalario"; // Vista del formulario para buscar salario
    }

    @PostMapping("/mostrarSalario")
    public String mostrarSalario(@RequestParam String dni, Model model) {
        Optional<Integer> salarioOpt = empleadoService.obtenerSalarioPorDni(dni);

        if (salarioOpt.isPresent()) {
            model.addAttribute("dni", dni);
            model.addAttribute("salario", salarioOpt.get());
            return "empresa/mostrarSalario"; // Vista que muestra el salario del empleado
        } else {
            model.addAttribute("mensajeError", "No se encontró un empleado con el DNI proporcionado.");
            return "empresa/error"; // Vista de error
        }
    }

    @GetMapping("/buscarEmpleados")
    public String buscarEmpleados() {
        return "empresa/buscarEmpleados"; // Vista del formulario para buscar empleados con filtros
    }

    @PostMapping("/mostrarEmpleadosFiltrados")
    public String mostrarEmpleadosFiltrados(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) Character sexo,
            @RequestParam(required = false) Integer categoria,
            @RequestParam(required = false) Integer anyos,
            Model model) {

        // Filtrar empleados usando el servicio con lógica de coincidencias parciales
        List<Empleado> empleados = empleadoService.filtrarEmpleados(
                (root, query, criteriaBuilder) -> {
                    var predicates = criteriaBuilder.conjunction(); // Predicados vacíos para encadenar

                    // Filtrar por nombre (coincidencia parcial)
                    if (nombre != null && !nombre.isEmpty()) {
                        predicates = criteriaBuilder.and(predicates,
                                criteriaBuilder.like(root.get("nombre"), "%" + nombre + "%"));
                    }

                    // Filtrar por DNI (coincidencia parcial)
                    if (dni != null && !dni.isEmpty()) {
                        predicates = criteriaBuilder.and(predicates,
                                criteriaBuilder.like(root.get("dni"), "%" + dni + "%"));
                    }

                    // Filtrar por sexo (coincidencia exacta)
                    if (sexo != null) {
                        predicates = criteriaBuilder.and(predicates,
                                criteriaBuilder.equal(root.get("sexo"), sexo));
                    }

                    // Filtrar por categoría (coincidencia exacta)
                    if (categoria != null) {
                        predicates = criteriaBuilder.and(predicates,
                                criteriaBuilder.equal(root.get("categoria"), categoria));
                    }

                    // Filtrar por años de experiencia (coincidencia exacta)
                    if (anyos != null) {
                        predicates = criteriaBuilder.and(predicates,
                                criteriaBuilder.equal(root.get("anyos"), anyos));
                    }

                    return predicates;
                });

        model.addAttribute("empleados", empleados);
        return "empresa/mostrarEmpleados"; // Vista que muestra empleados filtrados
    }


    @GetMapping("/modificarEmpleado")
    public String modificarEmpleado(@RequestParam String dni, Model model) {
        Empleado empleadoOpt = empleadoService.buscarEmpleadoPorDni(dni);
        model.addAttribute("empleado", empleadoOpt);
        return "empresa/modificarEmpleado";
    }

    @PostMapping("/enviarCambios")
    public String enviarCambios(@RequestParam String dni, @RequestParam String nombre, @RequestParam String sexo,
                                @RequestParam Integer categoria, @RequestParam Integer anyos, Model model) {
        empleadoService.modificarEmpleado(dni, nombre, sexo, categoria, anyos);
        model.addAttribute("exito", true);
        return mostrarEmpleados(model);
    }




    @GetMapping("/error")
    public String error(@RequestParam String mensaje, Model model) {
        model.addAttribute("mensaje", mensaje);
        return "empresa/error"; // Vista genérica de error
    }
}
