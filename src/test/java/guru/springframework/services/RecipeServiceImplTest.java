package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.*;
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
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
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
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeCommandByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById(1L);

        assertNotNull(commandById, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipesTest() {

        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());

        // verify how many times was recipeRepository.findAll was called
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    void findByIdTest() {
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
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteById() {
        //given
        Long idToDelete = 2L;

        //when
        recipeService.deleteById(idToDelete);

        //then
        verify(recipeRepository).deleteById(anyLong());
    }
}