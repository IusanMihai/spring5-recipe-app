package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    public Ingredient convert(IngredientCommand command) {
        if (command == null) {
            return null;
        }
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(command.getId());
        ingredient.setDescription(command.getDescription());
        ingredient.setAmount(command.getAmount());
        ingredient.setUom(uomConverter.convert(command.getUom()));
        return ingredient;
    }
}
