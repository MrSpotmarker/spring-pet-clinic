package at.jhofer.demo.services;

import at.jhofer.demo.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
