package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.model.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
