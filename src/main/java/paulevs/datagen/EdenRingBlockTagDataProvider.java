package paulevs.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import org.betterx.bclib.api.v3.datagen.TagDataProvider;
import org.betterx.wover.tag.api.TagManager;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;
import paulevs.edenring.EdenRing;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class EdenRingBlockTagDataProvider extends TagData<Block> {
    public EdenRingBlockTagDataProvider(
            FabricDataOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture
    ) {
        super(
                TagManager.BLOCKS,
                List.of(EdenRing.MOD_ID),
                Set.of(CommonBlockTags.NETHER_MYCELIUM),
                output,
                registriesFuture
        );
    }
}
