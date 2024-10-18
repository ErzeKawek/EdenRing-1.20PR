package paulevs.edenring.world.biomes.cave;

import org.betterx.bclib.interfaces.SurfaceMaterialProvider;

import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.betterx.wover.surface.api.SurfaceRuleBuilder;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenBiomeBuilder;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class ErodedCaveBiome extends EdenRingBiome.Config {
    public ErodedCaveBiome() {
        super(EdenBiomes.ERODED_CAVE.location());
    }

    @Override
    protected void addCustomBuildData(EdenBiomeBuilder builder) {
        BiomesCommonMethods.addDefaultLandFeatures(builder);
        builder
                .plantsColor(0x707c47)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.DRIPSTONE_CLUSTER)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.LARGE_DRIPSTONE)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.POINTED_DRIPSTONE);
    }

    @Override
    protected SurfaceMaterialProvider surfaceMaterial() {
        return new EdenRingBiome.DefaultSurfaceMaterialProvider() {
            @Override
            public BlockState getTopMaterial() {
                return DRIPSTONE_BLOCK;
            }

            @Override
            public boolean generateSubSurfaceRule() {
                return false;
            }

            @Override
            public SurfaceRuleBuilder surface() {
                return super.surface().subsurface(DRIPSTONE_BLOCK, 3);
            }
        };
    }
}
