package com.talrey.torquetricks.block;

import com.simibubi.create.content.contraptions.relays.gearbox.GearshiftTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

public class TileMechanicalBrake extends GearshiftTileEntity {
  int signal;
  boolean changed;

  public TileMechanicalBrake (TileEntityType<? extends GearshiftTileEntity> type) {
    super(type);
  }

  @Override
  public float calculateStressApplied () {
    return getBlockState().get(BlockMechanicalBrake.BRAKE_STRENGTH);
  }

  @Override
  public void tick () {
    super.tick();
    if (changed) {
      changed = false;
      detachKinetics();
      removeSource();
      signal = world.getRedstonePowerFromNeighbors(pos);
      attachKinetics();
    }
  }

  @Override
  public float getRotationSpeedModifier(Direction face) {
    return 1;
  }

  public void neighborChanged () {
    if (!hasWorld()) return;
    int newSignal = world.getRedstonePowerFromNeighbors(pos);
    if (signal != newSignal) {
      changed = true;
    }
  }
}
