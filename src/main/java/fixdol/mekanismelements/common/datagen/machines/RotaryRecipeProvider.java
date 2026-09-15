package fixdol.mekanismelements.common.datagen.machines;

import mekanism.api.chemical.Chemical;
import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.fluids.FluidStack;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.MekanismElements;
import mekanism.common.registries.MekanismGases;
import net.minecraft.resources.ResourceLocation;
import fixdol.mekanismelements.common.datagen.machines.RotaryRecipeProvider;


import mekanism.api.datagen.recipe.builder.RotaryRecipeBuilder;
import net.minecraft.world.level.material.Fluid;

public class RotaryRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        addRotary(recipeOutput, "ammonia",             MSFluids.AMMONIA.getFluid(),             MSGases.AMMONIA.get());
        addRotary(recipeOutput, "ammonium_nitrate",    MSFluids.AMMONIUM_NITRATE.getFluid(),    MSGases.AMMONIUM_NITRATE.get());
        // AQUA_REGIA: fluid comentado en MSFluids, descomentar cuando esté registrado
        addRotary(recipeOutput, "antimatter_fluid",    MSFluids.ANTIMATTER_FLUID.getFluid(),    MekanismGases.ANTIMATTER.get());
        addRotary(recipeOutput, "beryllium",           MSFluids.BERYLLIUM.getFluid(),           MSGases.BERYLLIUM.get());
        addRotary(recipeOutput, "bromine",             MSFluids.BROMINE.getFluid(),             MSGases.BROMINE.get());
        addRotary(recipeOutput, "compressed_air",      MSFluids.COMPRESSED_AIR.getFluid(),      MSGases.COMPRESSED_AIR.get());
        addRotary(recipeOutput, "helium",              MSFluids.HELIUM.getFluid(),              MSGases.HELIUM.get());
        addRotary(recipeOutput, "hydrogen_cyanide",    MSFluids.HYDROGEN_CYANIDE.getFluid(),    MSGases.HYDROGEN_CYANIDE.get());
        addRotary(recipeOutput, "iodine",              MSFluids.IODINE.getFluid(),              MSGases.IODINE.get());
        addRotary(recipeOutput, "methane",             MSFluids.METHANE.getFluid(),             MSGases.METHANE.get());
        // NETHERITE_ACID: fluid comentado en MSFluids, descomentar cuando esté registrado
        addRotary(recipeOutput, "nitric_acid",         MSFluids.NITRIC_ACID.getFluid(),         MSGases.NITRIC_ACID.get());
        addRotary(recipeOutput, "nitric_oxide",        MSFluids.NITRIC_OXIDE.getFluid(),        MSGases.NITRIC_OXIDE.get());
        addRotary(recipeOutput, "nitrogen",            MSFluids.NITROGEN.getFluid(),            MSGases.NITROGEN.get());
        addRotary(recipeOutput, "nitrogen_dioxide",    MSFluids.NITROGEN_DIOXIDE.getFluid(),    MSGases.NITROGEN_DIOXIDE.get());
        // POTASSIUM: no registrado en MSFluids, agregar cuando esté listo
        addRotary(recipeOutput, "potassium_chloride",  MSFluids.POTASSIUM_CHLORIDE.getFluid(),  MSGases.POTASSIUM_CHLORIDE.get());
        addRotary(recipeOutput, "potassium_cyanide",   MSFluids.POTASSIUM_CYANIDE.getFluid(),   MSGases.POTASSIUM_CYANIDE.get());
        addRotary(recipeOutput, "potassium_hydroxide", MSFluids.POTASSIUM_HYDROXIDE.getFluid(), MSGases.POTASSIUM_HYDROXIDE.get());
        addRotary(recipeOutput, "potassium_iodide",    MSFluids.POTASSIUM_IODIDE.getFluid(),    MSGases.POTASSIUM_IODIDE.get());
        addRotary(recipeOutput, "seawater",            MSFluids.SEAWATER.getFluid(),            MSGases.SEAWATER.get());
        addRotary(recipeOutput, "strontium",           MSFluids.STRONTIUM.getFluid(),           MSGases.STRONTIUM.get());
        addRotary(recipeOutput, "superheated_helium",  MSFluids.SUPERHEATED_HELIUM.getFluid(),  MSGases.SUPERHEATED_HELIUM.get());
        addRotary(recipeOutput, "xenon",               MSFluids.XENON.getFluid(),               MSGases.XENON.get());
        addRotary(recipeOutput, "yttrium",             MSFluids.YTTRIUM.getFluid(),             MSGases.YTTRIUM.get());
    }

    private static void addRotary(Consumer<FinishedRecipe> recipeOutput, String name, Fluid fluid, Chemical chemical) {
        RotaryRecipeBuilder.rotary(
                IngredientCreatorAccess.fluid().from(fluid, 1),
                IngredientCreatorAccess.gas().from(chemical, 1),
                new GasStack((mekanism.api.chemical.gas.Gas) chemical, 1),
                new FluidStack(fluid, 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, "rotary/" + name));
    }
}