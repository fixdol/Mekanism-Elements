package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.Chemical;
import mekanism.api.datagen.recipe.builder.RotaryRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registration.impl.DeferredChemical;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

public class RotaryRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        addRotary(recipeOutput, "ammonia",             MSFluids.AMMONIA,             MSGases.AMMONIA);
        addRotary(recipeOutput, "ammonium_nitrate",    MSFluids.AMMONIUM_NITRATE,    MSGases.AMMONIUM_NITRATE);
        // AQUA_REGIA: fluid comentado en MSFluids, descomentar cuando esté registrado
        addRotary(recipeOutput, "antimatter_fluid",    MSFluids.ANTIMATTER_FLUID,    MekanismChemicals.ANTIMATTER);
        addRotary(recipeOutput, "beryllium",           MSFluids.BERYLLIUM,           MSGases.BERYLLIUM);
        addRotary(recipeOutput, "bromine",             MSFluids.BROMINE,             MSGases.BROMINE);
        addRotary(recipeOutput, "compressed_air",      MSFluids.COMPRESSED_AIR,      MSGases.COMPRESSED_AIR);
        addRotary(recipeOutput, "helium",              MSFluids.HELIUM,              MSGases.HELIUM);
        addRotary(recipeOutput, "hydrogen_cyanide",    MSFluids.HYDROGEN_CYANIDE,    MSGases.HYDROGEN_CYANIDE);
        addRotary(recipeOutput, "iodine",              MSFluids.IODINE,              MSGases.IODINE);
        addRotary(recipeOutput, "methane",             MSFluids.METHANE,             MSGases.METHANE);
        // NETHERITE_ACID: fluid comentado en MSFluids, descomentar cuando esté registrado
        addRotary(recipeOutput, "nitric_acid",         MSFluids.NITRIC_ACID,         MSGases.NITRIC_ACID);
        addRotary(recipeOutput, "nitric_oxide",        MSFluids.NITRIC_OXIDE,        MSGases.NITRIC_OXIDE);
        addRotary(recipeOutput, "nitrogen",            MSFluids.NITROGEN,            MSGases.NITROGEN);
        addRotary(recipeOutput, "nitrogen_dioxide",    MSFluids.NITROGEN_DIOXIDE,    MSGases.NITROGEN_DIOXIDE);
        // POTASSIUM: no registrado en MSFluids, agregar cuando esté listo
        addRotary(recipeOutput, "potassium_chloride",  MSFluids.POTASSIUM_CHLORIDE,  MSGases.POTASSIUM_CHLORIDE);
        addRotary(recipeOutput, "potassium_cyanide",   MSFluids.POTASSIUM_CYANIDE,   MSGases.POTASSIUM_CYANIDE);
        addRotary(recipeOutput, "potassium_hydroxide", MSFluids.POTASSIUM_HYDROXIDE, MSGases.POTASSIUM_HYDROXIDE);
        addRotary(recipeOutput, "potassium_iodide",    MSFluids.POTASSIUM_IODIDE,    MSGases.POTASSIUM_IODIDE);
        addRotary(recipeOutput, "seawater",            MSFluids.SEAWATER,            MSGases.SEAWATER);
        addRotary(recipeOutput, "strontium",           MSFluids.STRONTIUM,           MSGases.STRONTIUM);
        addRotary(recipeOutput, "superheated_helium",  MSFluids.SUPERHEATED_HELIUM,  MSGases.SUPERHEATED_HELIUM);
        addRotary(recipeOutput, "xenon",               MSFluids.XENON,               MSGases.XENON);
        addRotary(recipeOutput, "yttrium",             MSFluids.YTTRIUM,             MSGases.YTTRIUM);
    }

    private static void addRotary(RecipeOutput recipeOutput, String name, Holder<Fluid> fluid, DeferredChemical<Chemical> chemical) {
        RotaryRecipeBuilder.rotary(
                IngredientCreatorAccess.fluid().fromHolder(fluid, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(chemical, 1),
                chemical.asTemplate(1),
                new FluidStackTemplate(fluid, 1)
        ).save(recipeOutput, MekanismElements.rl("rotary/" + name));
    }
}
