package com.talrey.createaddon.block;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.talrey.createaddon.Config;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class TileSpikes extends KineticTileEntity {
  private static final float AMPLITUDE = 0.5f;

  private int tickTimer;
  private float cachedSpeed;

  public TileSpikes(TileEntityType<? extends KineticTileEntity> typeIn) {
    super(typeIn);
    setLazyTickRate(20);
    tickTimer = 0;
    cachedSpeed = 0f;
  }

  @Override
  public void onSpeedChanged (float previousSpeed) {
    cachedSpeed = 257-Math.abs(getSpeed());
    if (cachedSpeed > 256) cachedSpeed = 0f;
    markDirty();
  }

  @Override
  public float calculateStressApplied () {
    return Config.SPIKES_STRESS;
  }

  @Override
  public void tick() {
    super.tick();

    if (cachedSpeed > 256) return; // zero speed causes this

    if (tickTimer >= cachedSpeed) {
      tickTimer = 0;
    } else {
      tickTimer++;
    }
    if (!world.isRemote && getSpikeHeight() > 0) {
      List<Entity> list = getEntities(world, pos);
      if (!list.isEmpty()) {
        for (Entity e : list) doEntityDamage(world, pos, e);
      }
    }
  }

  @Override
  @Nullable
  public SUpdateTileEntityPacket getUpdatePacket() {
    CompoundNBT nbtTagCompound = new CompoundNBT();
    this.write (nbtTagCompound);
    return new SUpdateTileEntityPacket (pos, 42, nbtTagCompound); // 42 = tile entity type value
  }

  @Override
  public CompoundNBT getUpdateTag () {
    CompoundNBT nbtTagCompound = new CompoundNBT();
    this.write(nbtTagCompound);
    return (nbtTagCompound);
  }

  @Override
  public void handleUpdateTag (CompoundNBT tag) {
    this.read(tag);
  }

  @Override
  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    this.read(pkt.getNbtCompound());
  }

  @Override
  public CompoundNBT write (CompoundNBT parentTag) {
    super.write(parentTag);
    parentTag.putFloat("speed", cachedSpeed);
    parentTag.putInt(  "timer", tickTimer);
    return parentTag;
  }

  @Override
  public void read (CompoundNBT parentTag) {
    super.read(parentTag);
    if (parentTag.contains("speed", Constants.NBT.TAG_FLOAT)) {
      float floatIn = parentTag.getFloat("speed");
      if (-256f <= floatIn && floatIn <= 256f) {
        cachedSpeed = floatIn;
      }
      else cachedSpeed = 0;
    }
    if (parentTag.contains("timer", Constants.NBT.TAG_INT)) {
      int intIn = parentTag.getInt("timer");
      if (0 <= intIn && intIn <= cachedSpeed) {
        tickTimer = intIn;
      }
      else tickTimer = 0;
    }
  }

  private void doEntityDamage (World world, BlockPos pos, Entity entity) {
    entity.attackEntityFrom(DamageSource.CACTUS, Config.SPIKES_DAMAGE);
  }

  public float getSpikeHeight () {
    // if cachedSpeed is really fast or slow, then switch to a square wave instead of sine
    return (cachedSpeed > 1) ? (float)Math.sin(tickTimer/cachedSpeed * 16*Math.PI)*AMPLITUDE : (tickTimer>0 ? 1 : -1)*AMPLITUDE;
  }

  public List<Entity> getEntities (World world, BlockPos pos) {
    return world.getEntitiesWithinAABB(Entity.class, getDetectionBox(pos), (Predicate<Entity>)null);
  }

  private AxisAlignedBB getDetectionBox (BlockPos pos) {
    return new AxisAlignedBB(
    (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(),
    (double)pos.getX()+1, pos.getY()+2d, (double)pos.getZ()+1
    );
  }
}
