package at.jhofer.demo.model.repositories;

import at.jhofer.demo.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, Long> {

}
