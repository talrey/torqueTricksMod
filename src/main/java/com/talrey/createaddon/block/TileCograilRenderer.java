package com.talrey.createaddon.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.encased.EncasedShaftRenderer;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction.Axis;

public class TileCograilRenderer extends EncasedShaftRenderer {
  public static Quaternion ROTATION = new Quaternion (0f, 0.7f, 0f, 0.7f);

  private boolean secondPass;

  public TileCograilRenderer (TileEntityRendererDispatcher dispatcher) {
    super(dispatcher);
    secondPass = false;
  }

  @Override
  protected BlockState getRenderedBlockState (KineticTileEntity te) {
    if (secondPass) {
      return AllBlocks.LARGE_COGWHEEL.getDefaultState()
        .with(BlockStateProperties.AXIS, getRotationAxisOf(te))
      ;
    }
    else return super.getRenderedBlockState(te);
  }

  @Override
  protected void renderSafe(KineticTileEntity te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {
    super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    boolean xFacing = te.getBlockState().get(BlockStateProperties.FACING).getAxis().equals(Axis.X);
    secondPass = true;
    ms.translate((xFacing?0.3f:0.13f), 0.6f, (xFacing?0.13f:0.3f));
    ms.scale((xFacing?0.4f:0.74f), 0.4f, (xFacing?0.74f:0.4f));
    super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    secondPass = false;
  }
}
