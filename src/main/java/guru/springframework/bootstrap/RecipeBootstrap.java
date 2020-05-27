package guru.springframework.bootstrap;

import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository) {
        this.recipeRepository = recipeRepository;
        this.uomRepository = uomRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Loading Bootstrap data");
        recipeRepository.saveAll(getRecipes());
    }

    private UnitOfMeasure getUnitOfMeasure(String uomDescription) throws RuntimeException {
        Optional<UnitOfMeasure> optionalUom = uomRepository.findByDescription(uomDescription);
        if (!optionalUom.isPresent()) {
            throw new RuntimeException("Unknown unit of measure: " + uomDescription);
        }
        return optionalUom.get();
    }

    private List<Recipe> getRecipes() throws RuntimeException {

        List<Recipe> recipes = new ArrayList<>(2);

        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setDirections(
                "1. Cut the avocado, remove flesh\n" +
                        "2. Mash with a fork\n" +
                        "3. Add salt, lime juice, and the rest\n" +
                        "4. Serve"
        );

        UnitOfMeasure avocadoUom = getUnitOfMeasure("One Avocado");
        UnitOfMeasure teaspoonUom = getUnitOfMeasure("Teaspoon");
        UnitOfMeasure tableSpoonUom = getUnitOfMeasure("Tablespoon");
        UnitOfMeasure poundUom = getUnitOfMeasure("Pound");

        Ingredient avocado = new Ingredient("avocado", new BigDecimal(2), avocadoUom);
        guacamole.addIngredient(avocado);

        Ingredient salt = new Ingredient("salt", new BigDecimal(0.25), teaspoonUom);
        guacamole.addIngredient(salt);

        Ingredient limeJuice = new Ingredient("lime juice", new BigDecimal(1), tableSpoonUom);
        guacamole.addIngredient(limeJuice);

        recipes.add(guacamole);

        Recipe chickenTacos = new Recipe();
        chickenTacos.setDescription("Spicy Grilled Chicken Tacos");

        chickenTacos.setDirections(
                "1. Prepare a gas or charcoal grill for medium-high, direct heat\n" +
                        "2. Make the marinade and coat the chicken\n" +
                        "3. Grill the chicken\n" +
                        "4. Warm the tortillas\n" +
                        "5. Assemble the tacos\n"
        );

        Ingredient orangeJuice = new Ingredient("orange juice", new BigDecimal(3), tableSpoonUom);
        chickenTacos.addIngredient(orangeJuice);

        Ingredient chickenThighs = new Ingredient("chicken thighs", new BigDecimal(1.25), poundUom);
        chickenTacos.addIngredient(chickenThighs);

        recipes.add(chickenTacos);

        return recipes;
    }
}
