package com.awn.appnominaspring.service;

import com.awn.appnominaspring.entity.Empleado;
//import com.awn.appnominaspring.entity.Nominas;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface IEmpleadoService {

    Empleado obtenerEmpleadoPorDni(String dni);
    List<Empleado> obtenerTodosLosEmpleados();
    Empleado guardarEmpleado(Empleado empleado);
    Empleado actualizarEmpleado(String dni, Empleado empleado);
    void eliminarEmpleado(String dni);
    List<Empleado> obtenerEmpleadosFiltrados(String nombre, String dni, String sexo, Integer categoria, Integer anyos);
//    Nominas calcularNomina(String dni);
}

