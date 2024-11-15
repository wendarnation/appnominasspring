package com.awn.appnominaspring.service;

import com.awn.appnominaspring.entity.Empleado;
import com.awn.appnominaspring.entity.Nominas;
import com.awn.appnominaspring.repository.EmpleadoRepository;
import com.awn.appnominaspring.repository.NominasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService implements IEmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final NominasRepository nominasRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository, NominasRepository nominasRepository) {
        this.empleadoRepository = empleadoRepository;
        this.nominasRepository = nominasRepository;
    }

    @Override
    public Empleado obtenerEmpleadoPorDni(String dni) {
        return empleadoRepository.findById(dni).orElseThrow(() ->
                new IllegalArgumentException("Empleado no encontrado con DNI: " + dni));
    }

    @Override
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado guardarEmpleado(Empleado empleado) {
        if (empleadoRepository.existsById(empleado.getDni())) {
            throw new IllegalArgumentException("Ya existe un empleado con el DNI: " + empleado.getDni());
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado actualizarEmpleado(String dni, Empleado empleado) {
        Empleado empleadoExistente = empleadoRepository.findById(dni).orElseThrow(() ->
                new IllegalArgumentException("No se puede actualizar. Empleado no encontrado con DNI: " + dni));
        empleadoExistente.setNombre(empleado.getNombre());
        empleadoExistente.setSexo(empleado.getSexo());
        empleadoExistente.setCategoria(empleado.getCategoria());
        empleadoExistente.setAnyos(empleado.getAnyos());
        return empleadoRepository.save(empleadoExistente);
    }

    @Override
    public void eliminarEmpleado(String dni) {
        Empleado empleado = empleadoRepository.findById(dni).orElseThrow(() ->
                new IllegalArgumentException("No se puede eliminar. Empleado no encontrado con DNI: " + dni));
        empleadoRepository.delete(empleado);
    }

    @Override
    public Nominas calcularNomina(String dni) {
        Empleado empleado = empleadoRepository.findById(dni).orElseThrow(() ->
                new IllegalArgumentException("Empleado no encontrado con DNI: " + dni));
        Nominas nomina = new Nominas(empleado);
        nominasRepository.save(nomina);
        return nomina;
    }
}
