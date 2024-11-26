package paulevs.edenring.world.features.terrain;

import net.minecraft.world.level.block.Blocks;
import paulevs.edenring.world.features.basic.ScatterFeature;

import static paulevs.edenring.registries.EdenFeatures.inlineBuild;

public class MossLayer {
    public static final MossLayer MOSS_LAYER
            = inlineBuild("moss_layer", inlineBuild("moss_layer", new ScatterFeature(Blocks.MOSS_CARPET)), 4);
}
