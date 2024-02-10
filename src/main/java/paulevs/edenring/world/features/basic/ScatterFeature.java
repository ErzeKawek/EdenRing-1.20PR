package paulevs.edenring.world.features.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.bclib.util.MHelper;

public class ScatterFeature extends DefaultFeature {
	private Block block;
	
	public ScatterFeature(Block block) {
		this.block = block;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		RandomSource random = featurePlaceContext.random();
		BlockPos center = featurePlaceContext.origin();
		WorldGenLevel level = featurePlaceContext.level();
		
		BlockState state = block.defaultBlockState();
		MutableBlockPos pos = new MutableBlockPos();
		int count = getCount(random);
		for (int i = 0; i < count; i++) {
			int px = center.getX() + Mth.floor(Mth.clamp(random.nextGaussian() * 2 + 0.5F, -8, 8));
			int pz = center.getZ() + Mth.floor(Mth.clamp(random.nextGaussian() * 2 + 0.5F, -8, 8));
			pos.setX(px);
			pos.setZ(pz);
			for (int y = 5; y > -5; y--) {
				pos.setY(center.getY() + y);
				if (level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
					pos.setY(pos.getY() + 1);
					if (level.getBlockState(pos).isAir() && block.canSurvive(state, level, pos)) {
						placeBlock(level, pos, state);
						break;
					}
				}
			}
		}
		
		return true;
	}
	
	protected int getCount(RandomSource random) {
		return MHelper.randRange(10, 20, random);
	}
	protected void placeBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
		BlocksHelper.setWithoutUpdate(level, pos, state);
	}
}
