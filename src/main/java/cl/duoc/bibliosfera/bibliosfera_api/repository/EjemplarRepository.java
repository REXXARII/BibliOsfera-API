package cl.duoc.bibliosfera.bibliosfera_api.repository;

import cl.duoc.bibliosfera.bibliosfera_api.model.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {}