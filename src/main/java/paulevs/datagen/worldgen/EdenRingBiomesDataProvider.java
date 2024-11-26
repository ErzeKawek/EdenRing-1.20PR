package paulevs.datagen.worldgen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.minecraft.data.worldgen.BootstrapContext;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.betterx.wover.biome.api.builder.BiomeBootstrapContext;
import org.betterx.wover.biome.api.builder.BiomeBuilder;
import org.betterx.wover.feature.api.configured.ConfiguredFeatureKey;
import org.betterx.wover.feature.api.placed.PlacedConfiguredFeatureKey;
import org.betterx.wover.tag.api.TagManager;
import org.betterx.wover.tag.datagen.BiomeTagProvider;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBiomes;

import paulevs.edenring.world.biomes.EdenBiomeBuilder;
import paulevs.edenring.world.biomes.EdenBiomeKey;
import paulevs.edenring.world.biomes.EdenRingBiome;
import paulevs.edenring.world.biomes.air.AirOceanBiome;
import paulevs.edenring.world.biomes.air.GraviliteDebrisFieldBiome;
import paulevs.edenring.world.biomes.air.SkyColonyBiome;
import paulevs.edenring.world.biomes.cave.EmptyCaveBiome;
import paulevs.edenring.world.biomes.cave.ErodedCaveBiome;
import paulevs.edenring.world.biomes.land.BrainStormBiome;
import paulevs.edenring.world.biomes.land.GoldenForestBiome;
import paulevs.edenring.world.biomes.land.LakesideDesertBiome;
import paulevs.edenring.world.biomes.land.MycoticForestBiome;
import paulevs.edenring.world.biomes.land.OldMycoticForestBiome;
import paulevs.edenring.world.biomes.land.PulseForestBiome;
import paulevs.edenring.world.biomes.land.StoneGardenBiome;
import paulevs.edenring.world.biomes.land.WindValleyBiome;

public class EdenRingBiomesDataProvider extends BiomeTagProvider {
    public record BiomeInfo(EdenRingBiome.Config config, TagKey<Biome> tag,
                            ConfiguredFeatureKey configuredFeatureKey,
                            PlacedConfiguredFeatureKey placed) {
    }

    public static final Map<EdenBiomeKey<?, ?>, BiomeInfo> BIOMES = new HashMap<>();

    public static final List<EdenRingBiome> BIOMES_LAND = Lists.newArrayList();
    public static final List<EdenRingBiome> BIOMES_AIR = Lists.newArrayList();
    public static final List<EdenRingBiome> BIOMES_CAVE = Lists.newArrayList();

    // LAND //
    protected static final EdenRingBiome STONE_GARDEN = registerLand(new StoneGardenBiome());
    protected static final EdenRingBiome GOLDEN_FOREST = registerLand(new GoldenForestBiome());
    protected static final EdenRingBiome MYCOTIC_FOREST = registerLand(new MycoticForestBiome());
    protected static final EdenRingBiome PULSE_FOREST = registerLand(new PulseForestBiome());
    protected static final EdenRingBiome BRAINSTORM = registerLand(new BrainStormBiome());
    protected static final EdenRingBiome LAKESIDE_DESERT = registerLand(new LakesideDesertBiome());
    protected static final EdenRingBiome WIND_VALLEY = registerLand(new WindValleyBiome());

    // AIR //
    protected static final EdenRingBiome AIR_OCEAN = registerVoid(new AirOceanBiome());
    protected static final EdenRingBiome GRAVILITE_DEBRIS_FIELD = registerVoid(new GraviliteDebrisFieldBiome());
    protected static final EdenRingBiome SKY_COLONY = registerVoid(new SkyColonyBiome());

    // CAVES //
    protected static final EdenRingBiome EMPTY_CAVE = registerCave(new EmptyCaveBiome());
    protected static final EdenRingBiome ERODED_CAVE = registerCave(new ErodedCaveBiome());


    // SUBBIOMES //
    protected static final EdenRingBiome OLD_MYCOTIC_FOREST = registerSubBiome(new OldMycoticForestBiome(), MYCOTIC_FOREST);


    public EdenRingBiomesDataProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(TagManager.BIOMES, List.of(EdenRing.MOD_ID), output, registriesFuture);
    }

    protected void bootstrap(BiomeBootstrapContext context) {
        for (Map.Entry<EdenBiomeKey<?, ?>, BiomeInfo> e : BIOMES.entrySet()) {
            final EdenBiomeBuilder builder = e.getKey().bootstrap(context, e.getValue().config, e.getValue().tag);
            if (e.getValue().placed != null)
                builder.feature(e.getValue().placed);
            builder.register();
        }
    }

    public static void ensureStaticallyLoaded() {
    }

    private static EdenRingBiome registerBiome(EdenRingBiome.Config biomeConfig, BiomeAPI.BiomeType type) {
        return EdenRingBiome.create(biomeConfig, type);
    }

    private static EdenRingBiome registerLand(EdenRingBiome.Config biomeConfig) {
        EdenRingBiome biome = registerBiome(biomeConfig, EdenBiomes.EDEN_LAND);
        BIOMES_LAND.add(biome);
        return biome;
    }

    private static EdenRingBiome registerVoid(EdenRingBiome.Config biomeConfig) {
        EdenRingBiome biome = registerBiome(biomeConfig, EdenBiomes.EDEN_LAND);
        BIOMES_AIR.add(biome);
        return biome;
    }

    private static EdenRingBiome registerCave(EdenRingBiome.Config biomeConfig) {
        EdenRingBiome biome = registerBiome(biomeConfig, EdenBiomes.EDEN_LAND);
        BIOMES_CAVE.add(biome);
        return biome;
    }

    private static EdenRingBiome registerSubBiome(EdenRingBiome.Config biomeConfig, EdenRingBiome parent) {
        EdenRingBiome biome = EdenRingBiome.createSubBiome(biomeConfig, parent);
        BiomeType type = parent.getIntendedType();
        if (type == EdenBiomes.EDEN_LAND) {
            BIOMES_LAND.add(biome);
        } else if (type == EdenBiomes.EDEN_VOID) {
            BIOMES_AIR.add(biome);
        } else if (type == EdenBiomes.EDEN_CAVE) {
            BIOMES_CAVE.add(biome);
        }
        return biome;
    }

}
