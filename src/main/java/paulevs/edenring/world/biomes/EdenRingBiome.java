package paulevs.edenring.world.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.interfaces.SurfaceMaterialProvider;
import org.betterx.wover.biome.api.data.BiomeData;
import org.betterx.wover.biome.api.data.BiomeGenerationDataContainer;
import org.betterx.wover.generator.api.biomesource.WoverBiomeBuilder;
import org.betterx.wover.generator.api.biomesource.WoverBiomeData;
import org.betterx.wover.surface.api.SurfaceRuleBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.registries.EdenSounds;
import paulevs.edenring.world.generator.BiomeType;

import java.util.List;
import java.util.Optional;

public class EdenRingBiome extends WoverBiomeData implements SurfaceMaterialProvider {
    @SuppressWarnings("null")
    public static final MapCodec<EdenRingBiome> CODEC = codec(
            SurfaceMaterialProvider.CODEC.fieldOf("surface")
                    .orElse(Config.DEFAULT_MATERIAL)
                    .forGetter(o -> o.surfMatProv),
            EdenRingBiome::new
    );

    public static final KeyDispatchDataCodec<EdenRingBiome> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

    protected EdenRingBiome (
            float fogDensity,
            @NotNull ResourceKey<Biome> biome,
            @NotNull BiomeGenerationDataContainer generatorData,
            float terrainHeight,
            float genChance,
            int edgeSize,
            boolean vertical,
            @Nullable ResourceKey<Biome> edge,
            @Nullable ResourceKey<Biome> parent,
            SurfaceMaterialProvider surface
    ) {
        super(
                fogDensity, biome, generatorData, terrainHeight,
                genChance, edgeSize, vertical, edge, parent
        );
        this.surfMatProv = surface;
    }

    public void datagenSetup(BootstrapContext<BiomeData> dataContext) {
    }

    public KeyDispatchDataCodec<? extends WoverBiomeData> codec() {
        return KEY_CODEC;
    }

    public static class DefaultSurfaceMaterialProvider implements SurfaceMaterialProvider {
        public static final BlockState DIRT = Blocks.DIRT.defaultBlockState();
        public static final BlockState DRIPSTONE_BLOCK = Blocks.DRIPSTONE_BLOCK.defaultBlockState();
        public static final BlockState EDEN_GRASS_BLOCK = EdenBlocks.EDEN_GRASS_BLOCK.defaultBlockState();
        public static final BlockState EDEN_MOSS = EdenBlocks.EDEN_MOSS.defaultBlockState();
        public static final BlockState EDEN_MYCELIUM = EdenBlocks.EDEN_MYCELIUM.defaultBlockState();
        public static final BlockState STONE = Blocks.STONE.defaultBlockState();

        @Override
        public BlockState getTopMaterial() {
            return EDEN_GRASS_BLOCK;
        }

        @Override
        public BlockState getAltTopMaterial() {
            return getTopMaterial();
        }

        @Override
        public BlockState getUnderMaterial() {
            return DIRT;
        }

        public int subSurfaceDepth() {
            return 3;
        }

        @Override
        public boolean generateFloorRule() {
            return true;
        }

        public boolean generateSubSurfaceRule() {
            return true;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            SurfaceRuleBuilder builder = SurfaceRuleBuilder.start();

            if (generateFloorRule() && getTopMaterial() != getUnderMaterial()) {
                if (getTopMaterial() == getAltTopMaterial()) {
                    builder.floor(getTopMaterial());
                } else {
                    builder.chancedFloor(getTopMaterial(), getAltTopMaterial());
                }
            }
            if (generateSubSurfaceRule()) {
                builder.subsurface(getUnderMaterial(), subSurfaceDepth());
            }
            return builder;
        }
    }

    public abstract static class Config {
        public static final SurfaceMaterialProvider DEFAULT_MATERIAL = new DefaultSurfaceMaterialProvider();

        public final ResourceLocation ID;

        protected Config(String name) {
            this.ID = EdenRing.makeID(name);
        }

        protected Config(ResourceLocation ID) {
            this.ID = ID;
        }

        protected abstract void addCustomBuildData(EdenBiomeBuilder builder);

        public EdenBiomeBuilder.BiomeSupplier<EdenRingBiome> getSupplier() {
            return EdenRingBiome::new;
        }

        protected SurfaceMaterialProvider surfaceMaterial() {
            return DEFAULT_MATERIAL;
        }
    }

    public EdenRingBiome(ResourceKey<Biome> biomeID, WoverBiomeData settings) {
        super(biomeID, settings);
    }

    public static EdenRingBiome create(Config biomeConfig, BiomeAPI.BiomeType type) {
        return create(biomeConfig, type, null);
    }

    public static EdenRingBiome createSubBiome(Config data, @NotNull WoverBiomeBuilder.WoverBiome parentBiome) {
        return create(data, parentBiome.parent(), parentBiome);
    }


    public static EdenRingBiome create(Config biomeConfig, BiomeType type, WoverBiomeData parentBiome) {
        EdenBiomeBuilder builder = EdenBiomeBuilder
                .start(biomeConfig.ID)
                .music(EdenSounds.MUSIC_COMMON)
                .waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(183, 212, 255)
                .skyColor(113, 178, 255)
                .surface(biomeConfig.surfaceMaterial().surface().build())
                .type(type);

        // Check if parentBiome is not null before setting it
    }

    protected SurfaceMaterialProvider surfMatProv = Config.DEFAULT_MATERIAL;

    protected void setSurfaceMaterial(SurfaceMaterialProvider prov) {
        this.surfMatProv = prov;
    }

    @Override
    public BlockState getTopMaterial() {
        return this.surfMatProv.getTopMaterial();
    }

    @Override
    public BlockState getUnderMaterial() {
        return this.surfMatProv.getUnderMaterial();
    }

    @Override
    public BlockState getAltTopMaterial() {
        return this.surfMatProv.getAltTopMaterial();
    }

    @Override
    public boolean generateFloorRule() {
        return this.surfMatProv.generateFloorRule();
    }

    @Override
    public SurfaceRuleBuilder surface() {
        return this.surfMatProv.surface();
    }

    public static BlockState findTopMaterial(WoverBiomeData biome) {
        return BiomeAPI.findTopMaterial(biome).orElse(Config.DEFAULT_MATERIAL.getTopMaterial());
    }

    public static BlockState findTopMaterial(WoverBiomeData biome) {
        return findTopMaterial(BiomeAPI.getBiome(biome));
    }

    public static BlockState findTopMaterial(WorldGenLevel world, BlockPos pos) {
        return findTopMaterial(BiomeAPI.getBiome(world.getBiome(pos)));
    }

    public static BlockState findUnderMaterial(WoverBiomeData biome) {
        return BiomeAPI.findUnderMaterial(biome).orElse(Config.DEFAULT_MATERIAL.getUnderMaterial());
    }

    public static BlockState findUnderMaterial(WorldGenLevel world, BlockPos pos) {
        return findUnderMaterial(BiomeAPI.getBiome(world.getBiome(pos)));
    }

    public static List<WoverBiomeData> getAllBeBiomes() {
        return WoverBiomeData.getAllBiomes(BiomeType.EDEN);
    }
}
