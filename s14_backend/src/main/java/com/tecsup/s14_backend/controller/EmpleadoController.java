package com.tecsup.s14_backend.controller;

import com.tecsup.s14_backend.excepciones.ResourceNotFoundException;
import com.tecsup.s14_backend.modelo.Empleado;
import com.tecsup.s14_backend.repositorio.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Listar todos los empleados
    @GetMapping("/empleados")
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    // Guardar un nuevo empleado
    @PostMapping("/empleados")
    public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    // Obtener un empleado por su ID
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> listarEmpleadoPorId(@PathVariable long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El empleado no existe: " + id));
        return ResponseEntity.ok(empleado);
    }

    // Actualizar un empleado por su ID
    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable long id, @RequestBody Empleado empleadoRequest) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El empleado no existe: " + id));

        empleado.setNombre(empleadoRequest.getNombre());
        empleado.setApellidos(empleadoRequest.getApellidos());
        empleado.setEmail(empleadoRequest.getEmail());

        Empleado empleadoActualizado = empleadoRepository.save(empleado);
        return ResponseEntity.ok(empleadoActualizado);
    }

    // Eliminar un empleado por su ID
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarEmpleado(@PathVariable long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El empleado no existe: " + id));

        empleadoRepository.delete(empleado);

        Map<String, Boolean> response = new HashMap<>();
        response.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
