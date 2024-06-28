package paulevs.edenring.world.biomes.air;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import paulevs.edenring.registries.*;
import paulevs.edenring.world.biomes.BiomesCommonMethods;
import paulevs.edenring.world.biomes.EdenRingBiome;

public class GraviliteDebrisFieldBiome extends EdenRingBiome.Config {
    public GraviliteDebrisFieldBiome() {
        super(EdenBiomes.GRAVILITE_DEBRIS_FIELD.location());
    }

    @Override
    protected void addCustomBuildData(BCLBiomeBuilder builder) {
        BiomesCommonMethods.addDefaultVoidFeatures(builder);
        builder
                .spawn(EdenEntities.DISKWING, 10, 1, 2)
                .fogDensity(1F)
                .fogColor(0x00d0ff)
                .waterColor(0x00c3ff)
                .plantsColor(0x44854d)
                .music(EdenSounds.MUSIC_COMMON)
                .feature(EdenFeatures.GRAVILITE_DEBRIS);
    }
}
