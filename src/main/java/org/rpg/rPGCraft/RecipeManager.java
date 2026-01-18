package org.rpg.rPGCraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.rpg.rPGCraft.CustomItemComponents.CustomItem;
import org.rpg.rPGCraft.Definitions.CustomItemDefinitions;

import java.util.HashMap;
import java.util.List;

public class RecipeManager
{
    public RecipeManager()
    {
        InitializeRecipes();
    }

    private void InitializeRecipes()
    {
        /// Shaped recipes
        // lesser_detoxifying_charm
        HashMap<Character, ItemStack> lesserDetoxifyingCharmIngredientMap = new HashMap<>();
        lesserDetoxifyingCharmIngredientMap.put('S', new ItemStack(Material.STRING));
        lesserDetoxifyingCharmIngredientMap.put('P', CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("purified_poison_gland")));

        AddShapedRecipe("lesser_detoxifying_charm", CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("lesser_detoxifying_charm")), new String[]{
                " S ",
                "S S",
                " P "
        }, lesserDetoxifyingCharmIngredientMap);

        // poison_gland_tipped_arrows
        HashMap<Character, ItemStack> poisonGlandTippedArrowsIngredientMap = new HashMap<>();
        poisonGlandTippedArrowsIngredientMap.put('A', Main.GetInstance().itemManager.MakePotionItemStack(new ItemStack(Material.TIPPED_ARROW), null, PotionType.POISON, List.of()));
        poisonGlandTippedArrowsIngredientMap.put('G', CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("poison_gland")));

        AddShapedRecipe("poison_gland_tipped_arrows", Main.GetInstance().itemManager.MakePotionItemStack(new ItemStack(Material.TIPPED_ARROW, 8), null, null, List.of(new PotionEffect(PotionEffectType.POISON, 1000, 1))), new String[]{
                "AAA",
                "AGA",
                "AAA"
        }, poisonGlandTippedArrowsIngredientMap);

        // reinforced_twig
        HashMap<Character, ItemStack> reinforcedTwigIngredientMap = new HashMap<>();
        reinforcedTwigIngredientMap.put('S', new ItemStack(Material.STICK));
        reinforcedTwigIngredientMap.put('C', new ItemStack(Material.COPPER_INGOT));

        AddShapedRecipe("reinforced_twig", CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("reinforced_twig")), new String[]{
                "S",
                "C",
                "S"
        }, reinforcedTwigIngredientMap);

        // reinforced_twig_wand
        HashMap<Character, ItemStack> reinforcedTwigWandIngredientMap = new HashMap<>();
        reinforcedTwigWandIngredientMap.put('A', new ItemStack(Material.AMETHYST_SHARD));
        reinforcedTwigWandIngredientMap.put('R', CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("reinforced_twig")));

        AddShapedRecipe("reinforced_twig_wand", CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("reinforced_twig_wand")), new String[]{
                "A",
                "R"
        }, reinforcedTwigWandIngredientMap);

        /// Shapeless recipes
        // purified poison gland
        HashMap<ItemStack, Integer> purifiedPoisonGlandIngredientMap = new HashMap<>();
        purifiedPoisonGlandIngredientMap.put(CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("poison_gland")), 1);
        purifiedPoisonGlandIngredientMap.put(Main.GetInstance().itemManager.MakePotionItemStack(new ItemStack(Material.POTION), null, PotionType.STRENGTH, List.of()) , 1);

        AddShapelessRecipe("purified_poison_gland", CustomItem.GetCustomItemStack(CustomItemDefinitions.GetCustomItemByID("purified_poison_gland")), purifiedPoisonGlandIngredientMap);
    }

    private void AddShapedRecipe(String recipe_id, ItemStack item, String[] shape, HashMap<Character, ItemStack> ingredients)
    {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(Main.GetInstance(), recipe_id), item);
        recipe.shape(shape);

        for (char key : ingredients.keySet())
        {
            recipe.setIngredient(key, ingredients.get(key));
        }

        Bukkit.addRecipe(recipe);
    }

    private void AddShapelessRecipe(String recipe_id, ItemStack item, HashMap<ItemStack, Integer> ingredients)
    {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(Main.GetInstance(), recipe_id), item);
        for (ItemStack itemStack : ingredients.keySet())
        {
            recipe.addIngredient(ingredients.get(itemStack), itemStack);
        }

        Bukkit.addRecipe(recipe);
    }
}
