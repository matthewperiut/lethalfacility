package com.matthewperiut.lethalfacility.gen.dimension;

import com.matthewperiut.lethalfacility.gen.biome.LethalBiomeSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.dimension.Dimension;
import net.modificationstation.stationapi.api.client.world.dimension.TravelMessageProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.lethalfacility.LethalFacility.see_override;
import static com.matthewperiut.lethalfacility.gen.dimension.LethalDimensions.MOD_ID;

@EnvironmentInterface(value = EnvType.CLIENT, itf = TravelMessageProvider.class)
public class LethalDimension extends Dimension implements TravelMessageProvider {
    public static final String
            ENTERING_MESSAGE = "gui." + Identifier.of(MOD_ID, "enter"),
            LEAVING_MESSAGE = "gui." + Identifier.of(MOD_ID, "exit");

    private final float[] colours = new float[4];

    public LethalDimension(int serialId) {
        id = serialId;
        hasCeiling = true;
    }

    @Override
    protected void initBiomeSource() {
        biomeSource = new LethalBiomeSource(1);
    }

    @Override
    public ChunkSource createChunkGenerator() {
        return new LethalChunkGenerator(world);
    }

    @Override
    public float getTimeOfDay(long time, float delta) {
        if (see_override)
            return 0.F;
        return 0.5F;
    }

    @Override
    protected void initBrightnessTable() {
        if (see_override) {
            super.initBrightnessTable();
            return;
        }
        float var1 = 0.1F;

        for(int var2 = 0; var2 <= 15; ++var2) {
            float var3 = 1.0F - (float)var2 / 15.0F;
            this.lightLevelToLuminance[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
        }

    }

    @Override
    public float[] getBackgroundColor(float time, float delta) {
        return new float[]{ 0.f, 0.f, 0.f, 0.f };
    }

    @Override
    public Vec3d getFogColor(float time, float delta) {
//        int i = 0x8080a0;
//        float f2 = MathHelper.cos(time * 3.141593F * 2.0F) * 2.0F + 0.5F;
//        if (f2 < 0.0F)
//            f2 = 0.0F;
//        if (f2 > 1.0F)
//            f2 = 1.0F;
//        float f3 = (float) (i >> 16 & 0xff) / 255F;
//        float f4 = (float) (i >> 8 & 0xff) / 255F;
//        float f5 = (float) (i & 0xff) / 255F;
//        f3 *= f2 * 0.94F + 0.06F;
//        f4 *= f2 * 0.94F + 0.06F;
//        f5 *= f2 * 0.91F + 0.09F;
        return Vec3d.createCached(0.f, 0.f, 0.f);
    }

    @Override
    public boolean hasGround() {
        return true;
    }

    @Override
    public float getCloudHeight() {
        return 0;
    }

    @Override
    public boolean isValidSpawnPoint(int x, int y) {
        return true;
    }

    @Override
    public boolean hasWorldSpawn() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public String getEnteringTranslationKey() {
        return ENTERING_MESSAGE;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public String getLeavingTranslationKey() {
        return LEAVING_MESSAGE;
    }
}
