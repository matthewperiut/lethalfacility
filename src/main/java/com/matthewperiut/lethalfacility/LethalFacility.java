package com.matthewperiut.lethalfacility;

import com.matthewperiut.lethalfacility.gen.control.RoomMap;
import com.matthewperiut.lethalfacility.util.command.StructureCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.modificationstation.stationapi.api.util.math.Vec3d;

public class LethalFacility implements ModInitializer
{
    public static boolean see_override = false;

    public static RoomMap roomMap = new RoomMap();

    public static boolean mojangfix = false;

    public static boolean worldLethalEntranceExists = false;
    public static String worldLethalEntranceFilePath = "";
    public static Vec3d worldLethalEntrancePos = null;

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
