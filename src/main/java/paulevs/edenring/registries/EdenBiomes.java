package paulevs.edenring.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeRegistry;
import org.betterx.bclib.api.v2.levelgen.biomes.BiomeAPI;
import org.jetbrains.annotations.NotNull;
import paulevs.edenring.EdenRing;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class EdenBiomes {

    // LAND //
    public static final ResourceKey<Biome> STONE_GARDEN = cKey("stone_garden");
    public static final ResourceKey<Biome> GOLDEN_FOREST = cKey("golden_forest");
    public static final ResourceKey<Biome> MYCOTIC_FOREST = cKey("mycotic_forest");
    public static final ResourceKey<Biome> PULSE_FOREST = cKey("pulse_forest");
    public static final ResourceKey<Biome> BRAINSTORM = cKey("brainstorm");
    public static final ResourceKey<Biome> LAKESIDE_DESERT = cKey("lakeside_desert");
    public static final ResourceKey<Biome> WIND_VALLEY = cKey("wind_valley");
    // public static final ResourceKey<Biome> CLOUD_FOREST = cKey("cloud_forest");

    // VOID //
    public static final ResourceKey<Biome> AIR_OCEAN = cKey("air_ocean");
    public static final ResourceKey<Biome> SKY_COLONY = cKey("sky_colony");
    public static final ResourceKey<Biome> GRAVILITE_DEBRIS_FIELD = cKey("gravilite_debris_field");

    // CAVES
    public static final ResourceKey<Biome> EMPTY_CAVE = cKey("empty_cave");
    public static final ResourceKey<Biome> ERODED_CAVE = cKey("eroded_cave");

    // SUBBIOMES //
    public static final ResourceKey<Biome> OLD_MYCOTIC_FOREST = cKey("old_mycotic_forest");
    public static final ResourceKey<Biome> OLD_SKY_COLONY = cKey("old_sky_colony");

    private static @NotNull ResourceKey<Biome> cKey(String path) {
        return ResourceKey.create(Registries.BIOME, EdenRing.makeID(path));
    }

    public static void register() {
        BCLBiomeRegistry.registerBiomeCodec(EdenRing.makeID("biome"), EdenRingBiome.KEY_CODEC);
    }
}
