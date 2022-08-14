package at.jhofer.demo.model.repositories;

import at.jhofer.demo.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {

}
