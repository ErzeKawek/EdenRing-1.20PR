package paulevs.datagen;

import java.util.List;

import org.betterx.bclib.api.v3.datagen.RegistrySupplier;
import org.betterx.wover.generator.api.biomesource.WoverBiomeData;
import org.betterx.wover.surface.api.AssignedSurfaceRule;
import org.betterx.wover.surface.api.SurfaceRuleRegistry;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import paulevs.edenring.EdenRing;
import paulevs.datagen.worldgen.EdenRingBiomesDataProvider;
import paulevs.datagen.worldgen.PlacedFeatureDataProvider;

public class EdenRingRegistrySupplier extends RegistrySupplier {
    public static final EdenRingRegistrySupplier INSTANCE = new EdenRingRegistrySupplier();

    protected EdenRingRegistrySupplier() {
        super(List.of(EdenRing.MOD_ID));
    }

    @Override
    protected List<RegistryInfo<?>> initializeRegistryList(@Nullable List<String> list) {
        return List.of(
                new RegistryInfo<>(
                        BCLBiomeRegistry.BCL_BIOMES_REGISTRY,
                        WoverBiomeData.CODEC
                ),
                new RegistryInfo<>(
                        SurfaceRuleRegistry.SURFACE_RULES_REGISTRY,
                        AssignedSurfaceRule.CODEC
                ),
                new RegistryInfo<>(
                        Registries.CONFIGURED_FEATURE,
                        ConfiguredFeature.DIRECT_CODEC
                        //ConfiguredFeatureDataProvider::bootstrap
                ),
                new RegistryInfo<>(
                        Registries.PLACED_FEATURE,
                        PlacedFeature.DIRECT_CODEC,
                        PlacedFeatureDataProvider::bootstrap
                ),
                new RegistryInfo<>(
                        Registries.BIOME,
                        Biome.DIRECT_CODEC,
                        EdenRingBiomesDataProvider::bootstrap
                )
        );
    }
}
