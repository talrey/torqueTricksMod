package com.talrey.torquetricks;

import com.talrey.torquetricks.item.*;
import com.talrey.torquetricks.block.*;

import com.tterrag.registrate.Registrate;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("torquetricks")
public class TorqueTricksMod
{
  // Directly reference a log4j logger.
  private static final Logger LOGGER = LogManager.getLogger();

  public static final String MOD_ID = "torquetricks";
  public static Registrate TTM_REGISTRATE;

  public TorqueTricksMod() {
    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
    IEventBus fmlbus = FMLJavaModLoadingContext.get().getModEventBus();
    fmlbus.register(ModBlocks.class);

    TTM_REGISTRATE = Registrate.create(MOD_ID);
    ModItems.register(TTM_REGISTRATE);
    ModBlocks.register(TTM_REGISTRATE);
  }
}
