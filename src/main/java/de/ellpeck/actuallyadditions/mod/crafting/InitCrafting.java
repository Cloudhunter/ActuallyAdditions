/*
 * This file ("InitCrafting.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.crafting;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.RecipeSorter;

public final class InitCrafting{

    public static void init(){
        ModUtil.LOGGER.info("Initializing Crafting Recipes...");

        ItemCrafting.init();
        BlockCrafting.init();
        MiscCrafting.init();
        FoodCrafting.init();
        ToolCrafting.init();

        ActuallyAdditionsAPI.addCompostRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.MASHED_FOOD.ordinal()), Blocks.LEAVES, new ItemStack(InitItems.itemFertilizer), Blocks.DIRT);
        ActuallyAdditionsAPI.addCompostRecipe(new ItemStack(InitItems.itemCanolaSeed), Blocks.DIRT, new ItemStack(InitItems.itemMisc, 1, TheMiscItems.BIOMASS.ordinal()), Blocks.SOUL_SAND);

        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidCanolaOil.getName(), 40);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidOil.getName(), 100);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidCrystalOil.getName(), 200);
        ActuallyAdditionsAPI.addOilGenRecipe(InitFluids.fluidEmpoweredOil.getName(), 350);

        RecipeSorter.register(ModUtil.MOD_ID+":recipeKeepDataShaped", RecipeKeepDataShaped.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
        RecipeSorter.register(ModUtil.MOD_ID+":recipeKeepDataShapeless", RecipeKeepDataShapeless.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
    }

}
