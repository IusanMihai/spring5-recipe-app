package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {

        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());

        // verify how many times was recipeRepository.findAll was called
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeByIdTest() {
        //given
        Long recipeId = 1L;
        Recipe givenRecipe = new Recipe();
        givenRecipe.setId(recipeId);
        Optional<Recipe> optionalRecipe = Optional.of(givenRecipe);
        when(recipeRepository.findById(recipeId)).thenReturn(optionalRecipe);
        //when
        Recipe returnedRecipe = recipeService.findById(recipeId);
        //then
        assertNotNull(returnedRecipe, "Null Recipe Returned");
        assertEquals(givenRecipe, returnedRecipe);
        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void findById() {
        //given
        Long recipeId = 1L;
        Recipe givenRecipe = new Recipe();
        givenRecipe.setId(recipeId);
        Optional<Recipe> optionalRecipe = Optional.of(givenRecipe);
        when(recipeRepository.findById(recipeId)).thenReturn(optionalRecipe);
        //when
        Recipe recipe = recipeService.findById(recipeId);
        //then
        assertEquals(givenRecipe, recipe);
        verify(recipeRepository, times(1)).findById(recipeId);
    }
}