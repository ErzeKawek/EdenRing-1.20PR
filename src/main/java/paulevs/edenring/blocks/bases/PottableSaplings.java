package paulevs.edenring.blocks.bases;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.betterx.bclib.behaviours.interfaces.BehaviourSapling;
import org.betterx.bclib.blocks.FeatureSaplingBlock;
import org.betterx.bclib.interfaces.SurvivesOn;
import paulevs.edenring.interfaces.PottedPlant;

public abstract class PottableSaplings<F extends Feature<FC>, FC extends FeatureConfiguration> extends FeatureSaplingBlock<F, FC> implements PottedPlant, BehaviourSapling, SurvivesOn {

    public PottableSaplings(FeatureSupplier<F, FC> featureSupplier) {
        super(featureSupplier);
    }

    public PottableSaplings(int light, FeatureSupplier<F, FC> featureSupplier) {
        super(light, featureSupplier);
    }


    @Override
    public boolean canPlantOn(Block block) {
        return isSurvivable(block.defaultBlockState());
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return isSurvivable(blockState);
    }
}
