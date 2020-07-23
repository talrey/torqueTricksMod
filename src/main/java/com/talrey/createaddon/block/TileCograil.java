package com.talrey.createaddon.block;

import com.talrey.createaddon.Config;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.List;

public class TileCograil extends KineticTileEntity {

  public TileCograil (TileEntityType<TileCograil> type) {
    super(type);
  }

  @Override
  public float calculateStressApplied () {
    return Config.COGRAIL_STRESS;
  }

  @Override
  public void onSpeedChanged(float previousSpeed) {
    super.onSpeedChanged(previousSpeed);
    Block myBlock = getBlockState().getBlock();
    List<AbstractMinecartEntity> found = ((BlockCograil)myBlock).getMinecarts(world, pos);
    if (found.size() > 0) {
      ((BlockCograil)myBlock).doBoostBehavior(getBlockState(), world, pos, found.get(0));
    }
  }
}
