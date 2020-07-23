package com.talrey.createaddon.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.relays.encased.EncasedShaftRenderer;
import com.talrey.createaddon.item.ModItems;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModBlocks {
  public static BlockEntry<BlockMechanicalBrake> R_BLOCK_MECHBRAKE;
  public static BlockEntry<BlockCograil> R_BLOCK_COGRAIL;

  // TODO put these in their own classes to implement workbench behavior: dbench, lbench

  public static void register (Registrate reg) {
    reg.itemGroup(ModItems::getItemGroup, "Torque Tricks");
    R_BLOCK_MECHBRAKE = reg.block("block_mechbrake", BlockMechanicalBrake::new)
    .tileEntity(TileMechanicalBrake::new).renderer(() -> EncasedShaftRenderer::new).build()
    .blockstate((ctx, prov) -> prov.directionalBlock(ctx.getEntry(),
    prov.models().getExistingFile(prov.modLoc("block/" + ctx.getName())
    )))
    .properties(p->p.nonOpaque())
    .recipe((ctx, prov) -> ShapedRecipeBuilder.shapedRecipe(R_BLOCK_MECHBRAKE.get())
    .patternLine(" s ").key('s', Ingredient.fromItems(Blocks.STONE))
    .patternLine("asc").key('a', Ingredient.fromItems(AllBlocks.SHAFT.get()))
    .patternLine(" s ").key('c', Ingredient.fromItems(AllBlocks.ANDESITE_CASING.get()))
    .addCriterion("has_ingredient", prov.hasItem(AllBlocks.ANDESITE_CASING.get())).build(prov::accept)
    )
    .simpleItem().item().properties(p -> p.group(ModItems.getItemGroup())).build()
    .lang("Analog Brake").register()
    ;
    R_BLOCK_COGRAIL = reg.block("block_cograil", BlockCograil::new)
    .tileEntity(TileCograil::new).renderer(()-> TileCograilRenderer::new).build()
    .blockstate((ctx, prov) -> prov.directionalBlock(ctx.getEntry(),
    prov.models().getExistingFile(prov.modLoc("block/" + ctx.getName())
    )))
    .properties(p->p.nonOpaque())
    .recipe((ctx, prov) -> ShapedRecipeBuilder.shapedRecipe(R_BLOCK_COGRAIL.get())
    .patternLine("axa").key('a', Ingredient.fromItems(AllItems.ANDESITE_ALLOY.get()))
    .patternLine("aca").key('x', Ingredient.fromItems(AllBlocks.COGWHEEL.get()))
    .patternLine("   ").key('c', Ingredient.fromItems(AllBlocks.ANDESITE_CASING.get()))
    .addCriterion("has_ingredient", prov.hasItem(AllItems.ANDESITE_ALLOY.get())).build(prov::accept)
    )
    .simpleItem().item().properties(p -> p.group(ModItems.getItemGroup())).build()
    .lang("Cart Accelerator").register()
    ;
  }

  @SubscribeEvent
  public static void FMLClientSetupEvent(FMLClientSetupEvent event) {
    RenderTypeLookup.setRenderLayer(R_BLOCK_COGRAIL.get(),   RenderType.getCutout());
    RenderTypeLookup.setRenderLayer(R_BLOCK_MECHBRAKE.get(), RenderType.getCutout());
  }
}
