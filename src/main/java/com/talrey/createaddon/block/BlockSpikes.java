package com.talrey.createaddon.block;

import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.tterrag.registrate.util.entry.TileEntityEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class BlockSpikes extends KineticBlock {

  public BlockSpikes(Properties properties) {
    super(properties);
    setDefaultState(getDefaultState()
    );
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    super.fillStateContainer(builder);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockItemUseContext ctx) {
    return super.getStateForPlacement(ctx)
    ;
  }

  @Override
  public boolean hasShaftTowards(IWorldReader world, BlockPos pos, BlockState state, Direction face) {
    return face == Direction.DOWN;
  }

  @Override
  public Direction.Axis getRotationAxis(BlockState blockState) {
    return Direction.Axis.Y;
  }

  @Override
  protected boolean hasStaticPart() {
    return true;
  }

  @Override
  public boolean hasIntegratedCogwheel(IWorldReader world, BlockPos pos, BlockState state) {
    return false;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState blockState, IBlockReader iBlockReader) {
    return TileEntityEntry.cast(ModBlocks.R_BLOCK_SPIKES.getSibling(ForgeRegistries.TILE_ENTITIES)).create();
  }
}
