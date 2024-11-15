package com.awn.appnominaspring.service;

import com.awn.appnominaspring.entity.Empleado;
import com.awn.appnominaspring.entity.Nominas;
import com.awn.appnominaspring.repository.EmpleadoRepository;
import com.awn.appnominaspring.repository.NominasRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService implements IEmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final NominasRepository nominasRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository, NominasRepository nominasRepository) {
        this.empleadoRepository = empleadoRepository;
        this.nominasRepository = nominasRepository;
    }

    @Override
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public List<Empleado> filtrarEmpleados(Specification<Empleado> specification) {
        return empleadoRepository.findAll(specification);
    }

    @Override
    public Empleado buscarEmpleadoPorDni(String dni) {
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(dni);
        return empleadoOpt.orElse(null);
    }

    @Override
    public Optional<Integer> obtenerSalarioPorDni(String dni) {
        return nominasRepository.findById(dni).map(Nominas::getSueldo);
    }

    @Override
    public boolean modificarEmpleado(String dni, String nombre, String sexo, Integer categoria, Integer anyos) {
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(dni);
        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            empleado.setNombre(nombre);
            empleado.setSexo(sexo);
            empleado.setCategoria(categoria);
            empleado.setAnyos(anyos);
            empleadoRepository.save(empleado);
            return true;
        } else {
            return false;
        }
    }
}

