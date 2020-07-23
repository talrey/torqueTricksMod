package com.talrey.createaddon.item;

import com.talrey.createaddon.block.ModBlocks;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class ModItems {
  // creates the tab for the mod items in creative mode
  public static ItemGroup itemGroup = new ItemGroup("createaddon") {
    @Override
    public ItemStack createIcon () {
      return new ItemStack(ModBlocks.R_BLOCK_MECHBRAKE.get());
    }
  };

  //public static ItemEntry<Item>
  //  R_ITEM_FOO
  //;

  public static void register (Registrate reg) {
    //
  }
}