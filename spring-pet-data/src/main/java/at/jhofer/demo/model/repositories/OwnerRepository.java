package at.jhofer.demo.model.repositories;

import at.jhofer.demo.model.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameLikeIgnoreCase(@Param("lastName") String lastName);
}
