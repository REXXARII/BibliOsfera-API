package cl.duoc.bibliosfera.bibliosfera_api.repository;

import cl.duoc.bibliosfera.bibliosfera_api.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {}