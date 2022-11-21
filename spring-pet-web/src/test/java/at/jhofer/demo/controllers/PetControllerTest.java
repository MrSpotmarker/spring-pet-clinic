package at.jhofer.demo.controllers;

import at.jhofer.demo.model.Owner;
import at.jhofer.demo.model.Pet;
import at.jhofer.demo.model.PetType;
import at.jhofer.demo.services.OwnerService;
import at.jhofer.demo.services.PetService;
import at.jhofer.demo.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    PetService petService;

    @Mock
    OwnerService ownerService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    private PetController petController;

    MockMvc mockMvc;
    Owner owner;
    Set<PetType> petTypes;

    @BeforeEach
    public void setUp() {
        owner = Owner.builder().id(1L).build();
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name(("Cat")).build());
        petTypes.add(PetType.builder().id(2L).name(("Dog")).build());

        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    public void initCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get("/owners/1/pets/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("owner"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void processCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/new"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    public void initUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());

        mockMvc.perform(get("/owners/1/pets/2/edit"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("owner"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void processUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post("/owners/1/pets/2/edit"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void populatePetTypes() {
        //todo impl
    }

    @Test
    void findOwner() {
        //todo impl
    }

    @Test
    void initOwnerBinder() {
        //todo impl
    }

}
