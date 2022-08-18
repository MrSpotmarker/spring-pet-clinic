package at.jhofer.demo.services.springdatajpa;

import at.jhofer.demo.model.Owner;
import at.jhofer.demo.model.repositories.OwnerRepository;
import at.jhofer.demo.model.repositories.PetRepository;
import at.jhofer.demo.model.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findAll() {
        Set<Owner> expectedSet = new HashSet<>();
        expectedSet.add(Owner.builder().id(1L).build());
        expectedSet.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(expectedSet);

        Set<Owner> actualSet = service.findAll();

        assertEquals(2L, actualSet.size());
        verify(ownerRepository).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(any())).thenReturn(Optional.ofNullable(returnOwner));

        Owner actualOwner = service.findById(1L);

        assertNotNull(actualOwner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());

        Owner actualOwner = service.findById(1L);

        assertNull(actualOwner);
    }

    @Test
    void save() {
        when(ownerRepository.save(returnOwner)).thenReturn(returnOwner);

        Owner owner = service.save(returnOwner);

        assertNotNull(owner);
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        service.delete(returnOwner);

        verify(ownerRepository).delete(any(Owner.class));
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(LAST_NAME)).thenReturn(returnOwner);

        Owner smith = service.findByLastName(LAST_NAME);

        assertEquals(returnOwner.getLastName(), smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }
}
