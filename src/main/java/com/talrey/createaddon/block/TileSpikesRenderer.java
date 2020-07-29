package com.talrey.createaddon.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.encased.EncasedShaftRenderer;
import com.simibubi.create.foundation.utility.SuperByteBuffer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;

public class TileSpikesRenderer extends EncasedShaftRenderer {
  private static final float OFFSET = 0.7f;

  private boolean secondPass;

  public TileSpikesRenderer(TileEntityRendererDispatcher dispatcher) {
    super(dispatcher);
    secondPass = false;
  }

  @Override
  protected void renderSafe(KineticTileEntity te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {
    ms.scale(1f, 0.8f, 1f);
    super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    secondPass = true;


    ms.translate(0f, ((TileSpikes)te).getSpikeHeight() + OFFSET, 0f);
    super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    secondPass = false;
  }

  @Override
  protected BlockState getRenderedBlockState (KineticTileEntity te) {
    if (secondPass) {
      return ModBlocks.R_BLOCK_SPIKES2.getDefaultState();
    }
    else return super.getRenderedBlockState(te);
  }

  @Override
  protected SuperByteBuffer getRotatedModel(KineticTileEntity te) {
    return super.getRotatedModel(te).rotateCentered(
      Direction.UP,
      secondPass ? -getAngleForTe(te, te.getPos(), Direction.Axis.Y) : 0f
    );
  }
}
