package paulevs.edenring.world.biomes.cave;

import org.betterx.bclib.interfaces.SurfaceMaterialProvider;

import net.minecraft.world.level.block.state.BlockState;
import paulevs.edenring.registries.EdenBiomes;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenBiomeBuilder;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class EmptyCaveBiome extends EdenRingBiome.Config {
    public EmptyCaveBiome() {
        super(EdenBiomes.EMPTY_CAVE.location());
    }

    @Override
    protected void addCustomBuildData(EdenBiomeBuilder builder) {
        BiomesCommonMethods.addDefaultLandFeatures(builder);
        builder.plantsColor(0x707c47);
    }

    @Override
    protected SurfaceMaterialProvider surfaceMaterial() {
        return new EdenRingBiome.DefaultSurfaceMaterialProvider() {

            @Override
            public BlockState getTopMaterial() {
                return STONE;
            }

            @Override
            public BlockState getUnderMaterial() {
                return STONE;
            }

            @Override
            public int subSurfaceDepth() {
                return 0;
            }
        };
    }
}
