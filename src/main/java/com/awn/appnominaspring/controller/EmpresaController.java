package com.awn.appnominaspring.controller;

import com.awn.appnominaspring.entity.Empleado;
//import com.awn.appnominaspring.entity.Nominas;
import com.awn.appnominaspring.service.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private IEmpleadoService empleadoService;

    @GetMapping("/inicio")
    public String inicio() {
        System.out.println("Accediendo a la página de inicio");
        return "inicio";
    }

    @GetMapping("/mostrarEmpleados")
    public String mostrarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
        model.addAttribute("empleados", empleados);
        return "empresa/mostrarEmpleados";
    }

    @GetMapping("/buscarSalario")
    public String buscarSalario() {
        return "empresa/buscarSalario";
    }

//    @PostMapping("/mostrarSalario")
//    public String mostrarSalario(@RequestParam String dni, Model model) {
//        try {
//            Nominas nomina = empleadoService.calcularNomina(dni);
//            model.addAttribute("dni", dni);
//            model.addAttribute("salario", nomina.getSueldoCalculado());
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("mensajeError", e.getMessage());
//        }
//        return "empresa/mostrarSalario";
//    }

    @GetMapping("/buscarEmpleados")
    public String buscarEmpleados() {
        return "empresa/buscarEmpleados";
    }

    @PostMapping("/mostrarEmpleadosFiltrados")
    public String mostrarEmpleadosFiltrados(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) Integer categoria,
            @RequestParam(required = false) Integer anyos,
            Model model) {
        List<Empleado> empleados = empleadoService.obtenerEmpleadosFiltrados(nombre, dni, sexo, categoria, anyos); // Simulación de filtrado.
        model.addAttribute("empleados", empleados);
        return "empresa/mostrarEmpleados";
    }

    @PostMapping("/modificarEmpleado")
    public String modificarEmpleado(@RequestParam String dni, Model model) {
        try {
            Empleado empleado = empleadoService.obtenerEmpleadoPorDni(dni);
            model.addAttribute("empleado", empleado);
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensajeError", e.getMessage());
            return "empresa/error";
        }
        return "empresa/modificarEmpleado";
    }

    @PostMapping("/enviarCambios")
    public String enviarCambios(@RequestParam String dni, Empleado empleado, Model model) {
        try {
            empleadoService.actualizarEmpleado(dni, empleado);
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensajeError", e.getMessage());
            return "empresa/error";
        }
        return "redirect:/empresa/mostrarEmpleados?exito=true";
    }

    @GetMapping("/error")
    public String error(@RequestParam String mensaje, Model model) {
        model.addAttribute("mensaje", mensaje);
        return "empresa/error";
    }
}
