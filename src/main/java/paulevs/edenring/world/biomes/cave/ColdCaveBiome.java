// package paulevs.edenring.world.biomes.cave;

//import net.minecraft.data.worldgen.placement.CavePlacements;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.levelgen.GenerationStep;
//import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
//import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
//import org.betterx.bclib.interfaces.SurfaceMaterialProvider;
//import paulevs.edenring.registries.EdenBiomes;
//import paulevs.edenring.world.biomes.BiomesCommonMethods;
//import paulevs.edenring.world.biomes.EdenRingBiome;

// public class ColdCaveBiome extends EdenRingBiome.Config {
//    public ColdCaveBiome() {
//        super(EdenBiomes.COLD_CAVE.location());
//    }

//    @Override
//    protected void addCustomBuildData(BCLBiomeBuilder builder) {
//        BiomesCommonMethods.addDefaultLandFeatures(builder);
//        builder
//                .plantsColor(0x707c47);
//    }

//    @Override
//    protected SurfaceMaterialProvider surfaceMaterial() {
//        return new EdenRingBiome.DefaultSurfaceMaterialProvider() {
//            @Override
//            public BlockState getTopMaterial() {
//                return DRIPSTONE_BLOCK;
//            }

//            @Override
//            public boolean generateSubSurfaceRule() {
//                return false;
//            }

//            @Override
//            public SurfaceRuleBuilder surface() {
//                return super.surface().subsurface(DRIPSTONE_BLOCK, 3);
//            }
//        };
//    }
//}
