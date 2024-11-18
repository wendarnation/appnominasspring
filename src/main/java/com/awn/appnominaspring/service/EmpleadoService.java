package com.awn.appnominaspring.service;

import com.awn.appnominaspring.entity.Empleado;
import com.awn.appnominaspring.entity.Nominas;
import com.awn.appnominaspring.repository.EmpleadoRepository;
import com.awn.appnominaspring.repository.NominasRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return empleadoRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un empleado con el DNI " + dni));
    }

    @Override
    public Optional<Integer> obtenerSalarioPorDni(String dni) {
        return nominasRepository.findById(dni).map(Nominas::getSueldo);
    }

    @Override
    public Specification<Empleado> crearFiltro(String nombre, String dni, String sexo, Integer categoria, Integer anyos) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null && !nombre.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("nombre"), "%" + nombre + "%"));
            }
            if (dni != null && !dni.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("dni"), "%" + dni + "%"));
            }
            if (sexo != null && !sexo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("sexo"), sexo));
            }
            if (categoria != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoria"), categoria));
            }
            if (anyos != null) {
                predicates.add(criteriaBuilder.equal(root.get("anyos"), anyos));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public void modificarEmpleado(String dni, String nombre, String sexo, Integer categoria, Integer anyos) {
        Empleado empleado = empleadoRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un empleado con el DNI " + dni));

        empleado.setNombre(nombre);
        empleado.setSexo(sexo);
        empleado.setCategoria(categoria);
        empleado.setAnyos(anyos);

        empleadoRepository.save(empleado);
    }
}

