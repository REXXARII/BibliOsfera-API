package cl.duoc.bibliosfera.bibliosfera_api.repository;

import cl.duoc.bibliosfera.bibliosfera_api.model.Edicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdicionRepository extends JpaRepository<Edicion, Long> {}