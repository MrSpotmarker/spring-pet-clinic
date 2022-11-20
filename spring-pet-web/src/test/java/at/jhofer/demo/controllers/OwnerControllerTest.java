package at.jhofer.demo.controllers;

import at.jhofer.demo.model.Owner;
import at.jhofer.demo.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());
        owners.add(Owner.builder().id(3L).build());

        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .build();
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/findOwners"))
            .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(List.of(
            Owner.builder().id(1L).build(),
            Owner.builder().id(2L).build()
            ));

        mockMvc.perform(get("/owners"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/ownersList"))
            .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(List.of(Owner.builder().id(1L).build()));

        mockMvc.perform(get("/owners"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void displayOwner() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/ownerDetails"))
            .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/createOrUpdateOwnerForm"))
            .andExpect(model().attributeExists("owner"));
        verifyNoInteractions(ownerService);
    }

    @Test
    void verifyCreationForm() throws Exception {
        when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(post("/owners/new"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"))
            .andExpect(model().attributeExists("owner"));
        verify(ownerService).save(any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        when(ownerService.findById(any())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/1/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/createOrUpdateOwnerForm"))
            .andExpect(model().attributeExists("owner"));

        verify(ownerService, times(0)).save(any(Owner.class));
    }

    @Test
    void processUpdateOwnerForm() throws Exception {
        when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(post("/owners/1/edit"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/1"))
            .andExpect(model().attributeExists("owner"));

        verify(ownerService).save(any());
    }

}
