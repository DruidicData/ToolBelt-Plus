package dev.gigaherz.toolbelt.integration;

import com.google.common.collect.Lists;
import dev.gigaherz.toolbelt.ConfigData;
import dev.gigaherz.toolbelt.ToolBelt;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import java.util.List;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
    private static final ResourceLocation ID = ToolBelt.location("jei_plugin");

    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        if (ConfigData.enableAnvilUpgrading)
        {
            IVanillaRecipeFactory vanillaFactory = registration.getVanillaRecipeFactory();
            List<ItemStack> pouch = Collections.singletonList(new ItemStack(ToolBelt.POUCH.get()));
            List<IJeiAnvilRecipe> anvilRecipes = new ArrayList<>();
            for (int i = 2; i < ToolBeltItem.MAX_SLOTS; i++)
            {
                anvilRecipes.add(vanillaFactory.createAnvilRecipe(ToolBeltItem.of(i), pouch, Collections.singletonList(ToolBeltItem.of(i + 1)), ToolBelt.location("upgrade_" + (i - 2) + "_to_" + (i - 1))));
            }
            registration.addRecipes(RecipeTypes.ANVIL, anvilRecipes);
        }
    }
}
