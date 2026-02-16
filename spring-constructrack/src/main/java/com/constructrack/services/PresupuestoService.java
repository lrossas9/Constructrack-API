package com.constructrack.services;

import com.constructrack.entities.Presupuesto;
import com.constructrack.entities.PresupuestoItem;
import com.constructrack.repositories.PresupuestoItemRepository;
import com.constructrack.repositories.PresupuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PresupuestoService {
    private final PresupuestoRepository presupuestoRepository;
    private final PresupuestoItemRepository presupuestoItemRepository;

    public List<Presupuesto> listarPresupuestos() {
        return presupuestoRepository.findByActivoTrue();
    }

    @SuppressWarnings("null")
    public Optional<Presupuesto> obtenerPresupuesto(Long id) {
        return presupuestoRepository.findById(id);
    }

    /**
     * Calcula los totales del presupuesto según los ítems y reglas de negocio
     */
    private void calcularTotales(Presupuesto presupuesto) {
        if (presupuesto.getItems() == null || presupuesto.getItems().isEmpty()) {
            presupuesto.setSubtotal(0.0);
            presupuesto.setAdministracion(0.0);
            presupuesto.setImprevistos(0.0);
            presupuesto.setUtilidad(0.0);
            presupuesto.setTotal(0.0);
            presupuesto.setGranTotal(0.0);
            return;
        }
        double subtotal = presupuesto.getItems().stream()
                .mapToDouble(i -> i.getValorTotal() != null ? i.getValorTotal() : 0.0)
                .sum();
        double administracion = subtotal * 0.23;
        double imprevistos = subtotal * 0.02;
        double utilidad = subtotal * 0.05;
        double total = subtotal + administracion + imprevistos + utilidad;
        // Aquí puedes sumar otros conceptos (PMT, PAGA, etc.) si los agregas
        double granTotal = total; // Por ahora igual al total

        presupuesto.setSubtotal(subtotal);
        presupuesto.setAdministracion(administracion);
        presupuesto.setImprevistos(imprevistos);
        presupuesto.setUtilidad(utilidad);
        presupuesto.setTotal(total);
        presupuesto.setGranTotal(granTotal);
    }

    @Transactional
    @SuppressWarnings("null")
    public Presupuesto crearPresupuesto(Presupuesto presupuesto) {
        // Calcular totales antes de guardar
        calcularTotales(presupuesto);
        return presupuestoRepository.save(presupuesto);
    }

    @Transactional
    @SuppressWarnings("null")
    public Presupuesto actualizarPresupuesto(Presupuesto presupuesto) {
        calcularTotales(presupuesto);
        return presupuestoRepository.save(presupuesto);
    }

    @Transactional
    @SuppressWarnings("null")
    public void eliminarPresupuesto(Long id) {
        presupuestoRepository.findById(id).ifPresent(p -> {
            p.setActivo(false);
            presupuestoRepository.save(p);
        });
    }

    public List<PresupuestoItem> listarItems(Long idPresupuesto) {
        return presupuestoItemRepository.findByPresupuestoIdPresupuesto(idPresupuesto);
    }

    /**
     * Agrega un ítem y actualiza los totales del presupuesto
     */
    @Transactional
    public PresupuestoItem agregarItem(PresupuestoItem item) {
        // Calcular valor total del ítem
        if (item.getCantidad() != null && item.getValorUnitario() != null) {
            item.setValorTotal(item.getCantidad() * item.getValorUnitario());
        } else {
            item.setValorTotal(0.0);
        }
        PresupuestoItem saved = presupuestoItemRepository.save(item);
        // Actualizar totales del presupuesto
        Presupuesto presupuesto = saved.getPresupuesto();
        presupuesto.setItems(presupuestoItemRepository.findByPresupuestoIdPresupuesto(presupuesto.getIdPresupuesto()));
        calcularTotales(presupuesto);
        presupuestoRepository.save(presupuesto);
        return saved;
    }

    /**
     * Actualiza un ítem y recalcula los totales del presupuesto
     */
    @Transactional
    public PresupuestoItem actualizarItem(PresupuestoItem item) {
        if (item.getCantidad() != null && item.getValorUnitario() != null) {
            item.setValorTotal(item.getCantidad() * item.getValorUnitario());
        } else {
            item.setValorTotal(0.0);
        }
        PresupuestoItem saved = presupuestoItemRepository.save(item);
        Presupuesto presupuesto = saved.getPresupuesto();
        presupuesto.setItems(presupuestoItemRepository.findByPresupuestoIdPresupuesto(presupuesto.getIdPresupuesto()));
        calcularTotales(presupuesto);
        presupuestoRepository.save(presupuesto);
        return saved;
    }
}
