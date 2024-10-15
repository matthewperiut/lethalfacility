package com.matthewperiut.lethalfacility;

import com.matthewperiut.lethalfacility.gen.roomcontrol.RoomMap;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.sound.SoundManager;

public class LethalFacility implements ModInitializer
{
    public static boolean see_override = false;

    public static RoomMap roomMap = new RoomMap();

    @Override
    public void onInitialize() {
        System.out.println("Hello World");

    }
}
