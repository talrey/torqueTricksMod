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
  private static ModItemGroup itemGroup;

  // creates the tab for the mod items in creative mode
  public static class ModItemGroup extends ItemGroup {
    public ModItemGroup (String name) { super(name); }
    @Override
    public ItemStack createIcon () {
      return new ItemStack(ModBlocks.R_BLOCK_MECHBRAKE.get());
    }
  };

  public static ModItemGroup getItemGroup () {
    if (itemGroup == null) {
      itemGroup = new ModItemGroup("torquetricks");
    }
    return itemGroup;
  }

  //public static ItemEntry<Item>
  //  R_ITEM_FOO
  //;

  public static void register (Registrate reg) {
    //reg.itemGroup(ModItems::getItemGroup, "Torque Tricks");
  }
}