package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    @InjectMocks
    RecipeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showById() throws Exception {
        //given
        Long recipeId = 1L;
        Recipe givenRecipe = new Recipe();
        givenRecipe.setId(recipeId);
        when(recipeService.findById(recipeId)).thenReturn(givenRecipe);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        //when
        //Recipe returnedRecipe = recipeService.findById(recipeId);
        //then
        mockMvc.perform(get("/recipe/show/" + recipeId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));

        verify(recipeService).findById(recipeId);
        verify(recipeService, never()).getRecipes();
    }
}