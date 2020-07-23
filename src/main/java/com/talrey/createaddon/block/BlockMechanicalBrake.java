package com.talrey.createaddon.block;

import com.simibubi.create.content.contraptions.relays.encased.GearshiftBlock;
import com.tterrag.registrate.util.entry.TileEntityEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockMechanicalBrake extends GearshiftBlock {

  static final IntegerProperty BRAKE_STRENGTH = IntegerProperty.create("brake", 0, 15);

  public BlockMechanicalBrake (Properties props) {
    super(props);
    props.nonOpaque();
    setDefaultState(getDefaultState()
      .with(BRAKE_STRENGTH, 0)
      .with(BlockStateProperties.FACING, Direction.NORTH)
    );
  }

  @Override
  public boolean hasTileEntity (BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState blockState, IBlockReader iBlockReader) {
    return TileEntityEntry.cast(ModBlocks.R_BLOCK_MECHBRAKE.getSibling(ForgeRegistries.TILE_ENTITIES)).create();
  }

  @Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (worldIn.isRemote)
			return;

		try {
      ((TileMechanicalBrake) worldIn.getTileEntity(pos)).neighborChanged();
    } catch (NullPointerException npe) {
		  System.out.println("Error: Missing Tile Entity for Brake!");
    }

    int newPower = worldIn.getRedstonePowerFromNeighbors(pos);
		if (state.get(BRAKE_STRENGTH) != newPower) {
		//	detachKinetics(worldIn, pos, true);
      worldIn.setBlockState(pos, state.with(BRAKE_STRENGTH, newPower));
		}
	}

  @Override
  protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
    builder.add(BRAKE_STRENGTH);
    builder.add(BlockStateProperties.FACING);
    super.fillStateContainer(builder);
  }

  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    return super.getStateForPlacement(context).with(BlockStateProperties.FACING, context.getNearestLookingDirection());
  }
}
