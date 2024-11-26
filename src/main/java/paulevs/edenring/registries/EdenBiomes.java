package paulevs.edenring.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeRegistry;
import org.betterx.bclib.api.v2.levelgen.biomes.BiomeAPI;
import org.betterx.wover.biome.api.data.BiomeCodecRegistry;
import paulevs.edenring.EdenRing;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class EdenBiomes {
	public static final BiomeAPI.BiomeType EDEN = new BiomeAPI.BiomeType("EDEN");
    public static final BiomeAPI.BiomeType EDEN_CAVE = new BiomeAPI.BiomeType("EDEN_CAVE", EDEN);
    public static final BiomeAPI.BiomeType EDEN_LAND = new BiomeAPI.BiomeType("EDEN_LAND", EDEN);
    public static final BiomeAPI.BiomeType EDEN_VOID = new BiomeAPI.BiomeType("EDEN_VOID", EDEN);

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
    public static final ResourceKey<Biome> GRAVILITE_DEBRIS_FIELD = cKey("gravilite_debris_field");
    public static final ResourceKey<Biome> SKY_COLONY = cKey("sky_colony");

    // CAVES
    public static final ResourceKey<Biome> EMPTY_CAVE = cKey("empty_cave");
    public static final ResourceKey<Biome> ERODED_CAVE = cKey("eroded_cave");
    // public static final ResourceKey<Biome> COLD_CAVE = cKey("cold_cave");

    // SUBBIOMES //
    public static final ResourceKey<Biome> OLD_MYCOTIC_FOREST = cKey("old_mycotic_forest");

    private static ResourceKey<Biome> cKey(String path) {
        return ResourceKey.create(Registries.BIOME, EdenRing.makeID(path));
    }

    public static void register() {
        BiomeCodecRegistry.register(EdenRing.C.mk("biome"), EdenRingBiome.KEY_CODEC);
    }
}
