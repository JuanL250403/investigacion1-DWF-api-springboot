package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.model.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
