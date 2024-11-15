package com.awn.appnominaspring.repository;

import com.awn.appnominaspring.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmpleadoRepository extends JpaRepository<Empleado, String>, JpaSpecificationExecutor<Empleado> {
}
