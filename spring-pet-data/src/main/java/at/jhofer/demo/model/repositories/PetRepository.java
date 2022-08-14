package at.jhofer.demo.model.repositories;

import at.jhofer.demo.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {

}
