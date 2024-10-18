package paulevs.edenring.world.biomes;

import org.betterx.bclib.interfaces.SurfaceMaterialProvider;
import org.betterx.wover.biome.api.BiomeKey;
import org.betterx.wover.biome.api.builder.BiomeBootstrapContext;
import org.betterx.wover.biome.api.data.BiomeData;
import org.betterx.wover.biome.api.data.BiomeGenerationDataContainer;
import org.betterx.wover.generator.api.biomesource.WoverBiomeBuilder;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenSounds;

public class EdenBiomeBuilder extends WoverBiomeBuilder.AbstractWoverBiomeBuilder<EdenBiomeBuilder> {
    protected boolean hasCave = false;
    protected SurfaceMaterialProvider surface;
    protected BiomeFactory biomeFactory;

    protected EdenBiomeBuilder(
            BiomeBootstrapContext context,
            BiomeKey<EdenBiomeBuilder> key,
            TagKey<Biome>... biomeTag
    ) {
        super(context, key);
        this.surface = EdenRingBiome.Config.DEFAULT_MATERIAL;
        this
                .music(EdenSounds.MUSIC_COMMON)
                .waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(183, 212, 255)
                .skyColor(113, 178, 255)
                .grassColor(255, 255, 255)
                .plantsColor(230, 63, 50)
                .mood(EdenSounds.AMBIENCE_WIND_VALLEY)
                .hasPrecipitation(false)
                .intendedPlacement(biomeTag.length > 0 ? biomeTag[0] : null)
                .tag(biomeTag);
    }

    public <B extends VanillaBuilder<B>> VanillaBuilder<B> grassColor(int i, int i1, int i2) {
        return null;
    }

    EdenBiomeBuilder configure(EdenRingBiome.Config biomeConfig) {
        this.startSurface()
                .rule(biomeConfig.surfaceMaterial().surface().build())
                .finishSurface();

        this.surface = biomeConfig.surfaceMaterial();

        return this;
    }

    public static <C extends EdenRingBiome.Config> EdenBiomeKey<C, ?> createKey(
            @NotNull String name
    ) {
        return new EdenBiomeKey<>(
                EdenRing.C.id(name.replace(' ', '_').toLowerCase()),
                null
        );
    }


    public static <C extends EdenRingBiome.Config, PC extends EdenRingBiome.Config> EdenBiomeKey<C, PC> createKey(
            @NotNull String name,
            @Nullable EdenBiomeKey<PC, ?> parentOrNull
    ) {
        return new EdenBiomeKey<>(
                EdenRing.C.id(name.replace(' ', '_').toLowerCase()),
                parentOrNull
        );
    }

    @Override
    public void registerBiomeData(BootstrapContext<BiomeData> dataContext) {
        final EdenRingBiome biome = biomeFactory.instantiateBiome(fogDensity, key, new BiomeGenerationDataContainer(parameters, intendedPlacement), terrainHeight, genChance, edgeSize, vertical, edge, parent, hasCave, surface);
        biome.datagenSetup(dataContext);
        dataContext.register(key.dataKey, biome);
    }

    @FunctionalInterface
    public interface BiomeFactory {
        @NotNull
        EdenRingBiome instantiateBiome(
                float fogDensity,
                BiomeKey<?> key,
                @NotNull BiomeGenerationDataContainer generatorData,
                float terrainHeight,
                float genChance,
                int edgeSize,
                boolean vertical,
                @Nullable ResourceKey<Biome> edge,
                @Nullable ResourceKey<Biome> parent,
                boolean hasCave,
                SurfaceMaterialProvider surface
        );
    }
}
