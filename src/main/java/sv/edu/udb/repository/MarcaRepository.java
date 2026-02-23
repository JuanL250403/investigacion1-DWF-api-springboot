package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.model.entity.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
