package com.talrey.createaddon.block;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock;
import com.talrey.createaddon.Config;
import com.tterrag.registrate.util.entry.TileEntityEntry;
import net.minecraft.block.*;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Predicate;

public class BlockCograil extends RotatedPillarKineticBlock {

  public BlockCograil (Properties properties) {
    super (properties);
    setDefaultState(getDefaultState()
      .with(BlockStateProperties.FACING, Direction.NORTH)
    );
  }

  @Override
  public boolean hasTileEntity (BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState blockState, IBlockReader iBlockReader) {
    return TileEntityEntry.cast(ModBlocks.R_BLOCK_COGRAIL.getSibling(ForgeRegistries.TILE_ENTITIES)).create();
  }

  @Override
  protected boolean hasStaticPart() { return true; }

  @Override
  public Direction.Axis getRotationAxis(BlockState blockState) {
    return blockState.get(AXIS);
  }

  @Override
  public boolean hasShaftTowards (IWorldReader reader, BlockPos pos, BlockState state, Direction face) {
    return face.getAxis() == state.get(AXIS);
  }

  @Override
  public VoxelShape getCollisionShape (BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {
    Entity collider = ctx.getEntity();
    if (collider != null) {
      if (collider instanceof AbstractMinecartEntity) {
        doBoostBehavior(state, collider.world, pos, collider);
        return VoxelShapes.empty();
      }
      else return VoxelShapes.fullCube();
    }
    return VoxelShapes.fullCube();
  }

  public void doBoostBehavior (BlockState state, World world, BlockPos pos, Entity collider) {
    List<AbstractMinecartEntity> carts = getMinecarts(world, pos);
    if (!carts.isEmpty() && carts.contains(collider)) {
      _doBoostBehavior(state, world, pos, collider);
    }
  }
  private void _doBoostBehavior (BlockState state, World world, BlockPos pos, Entity collider) {
    if (!world.isRemote) {
      TileEntity te = world.getTileEntity(pos);
      if (te instanceof KineticTileEntity) {
        double speed      =  -((KineticTileEntity)te).getSpeed() * Config.COGRAIL_SCALING;
        Vec3d direction   = collider.getLookVec().normalize();
        Vector3f forward  = state.get(BlockStateProperties.FACING).getUnitVector();
        if (forward.getX() < 0 || forward.getZ() > 0) speed = -speed;
        collider.addVelocity(forward.getX()*speed, 0d, forward.getZ()*speed);
        //System.out.println("speed="+speed);
        if (Math.abs(speed) > 0.1d) world.playSound(
          null, pos, SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.BLOCKS,
          /* Volume */ 100f, /* Pitch */ 0.8f
        );
      }
    }
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(BlockStateProperties.FACING);
    super.fillStateContainer(builder);
  }

  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    Direction facing = context.getPlacementHorizontalFacing();
    return super.getStateForPlacement(context)
      .with(BlockStateProperties.FACING, facing)
      .with(AXIS, facing.getAxis() == Axis.X ? Axis.Z : Axis.X)
    ;
  }

  public List<AbstractMinecartEntity> getMinecarts (World world, BlockPos pos) {
    return world.getEntitiesWithinAABB(AbstractMinecartEntity.class, getDetectionBox(pos), (Predicate<Entity>)null);
  }

  private AxisAlignedBB getDetectionBox (BlockPos pos) {
    return new AxisAlignedBB(
      (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(),
      (double)pos.getX()+1, pos.getY()+1.5d, (double)pos.getZ()+1
    );
  }
}
