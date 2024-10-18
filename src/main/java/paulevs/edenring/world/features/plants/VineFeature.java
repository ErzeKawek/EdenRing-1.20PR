package paulevs.edenring.world.features.plants;

import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.wover.block.api.BlockProperties;
import org.betterx.wover.block.api.BlockProperties.TripleShape;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.bclib.util.MHelper;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.world.features.basic.CeilScatterFeature;

public class VineFeature extends CeilScatterFeature {
	@Override
	protected void generate(WorldGenLevel level, MutableBlockPos pos, RandomSource random) {
		BlockState bottom = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM);
		BlockState middle = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE);
		BlockState top = EdenBlocks.EDEN_VINE.defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP);
		int length = MHelper.randRange(5, 10, random);
		int max = length - 1;
		for (int i = 0; i < length; i++) {
			if (level.getBlockState(pos).isAir()) {
				BlocksHelper.setWithoutUpdate(level, pos, i == 0 ? top : i == max ? bottom : middle);
			}
			else {
				pos.setY(pos.getY() + 1);
				BlocksHelper.setWithoutUpdate(level, pos, bottom);
				return;
			}
			pos.setY(pos.getY() - 1);
		}
	}
}
