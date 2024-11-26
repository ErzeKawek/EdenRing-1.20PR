package paulevs.edenring.interfaces;

import net.minecraft.world.level.block.Block;
import org.betterx.bclib.interfaces.SurvivesOnBlocks;
import paulevs.edenring.registries.EdenBlocks;

import java.util.List;

public interface SurvivesOnEdenGrass extends SurvivesOnBlocks {
    List<Block> BLOCKS = List.of(EdenBlocks.EDEN_GRASS_BLOCK);

    @Override
    default List<Block> getSurvivableBlocks() {
        return BLOCKS;
    }
}
