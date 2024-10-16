package com.matthewperiut.lethalfacility;

import com.matthewperiut.lethalfacility.gen.roomcontrol.RoomMap;
import com.matthewperiut.lethalfacility.util.command.StructureCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class LethalFacility implements ModInitializer
{
    public static boolean see_override = false;

    public static RoomMap roomMap = new RoomMap();

    public static boolean mojangfix = false;

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("mojangfixstationapi")) {
            mojangfix = true;
        }
        if (FabricLoader.getInstance().isModLoaded("retrocommands")) {
            StructureCommands.init();
        }
    }
}
