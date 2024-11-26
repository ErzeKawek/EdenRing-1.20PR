package paulevs.edenring.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.block.Block;

public interface PottedPlant {
    boolean canPlantOn(Block block);

    default boolean canBePotted() {
        return true;
    }

    @Environment(EnvType.CLIENT)
    default String getPottedState() {
        return "";
    }
}

