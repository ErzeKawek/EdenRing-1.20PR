package paulevs.edenring.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.bclib.util.MHelper;
import paulevs.edenring.registries.EdenBlocks;

public class LimphiumSapling extends OverlayPlantBlock {
	public LimphiumSapling() {
		super(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).randomTicks());
	}
	
	@Override
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
		return random.nextInt(8) == 0;
	}
	
	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		grow(level, random, pos, state);
	}
	
	public static void grow(WorldGenLevel level, RandomSource random, BlockPos pos, BlockState state) {
		int h = MHelper.randRange(2, 4, random);
		MutableBlockPos p = pos.mutable().setY(pos.getY() + 1);
		for (int i = 1; i < h; i++) {
			if (!level.getBlockState(p).isAir()) {
				h = i;
				break;
			}
			p.setY(p.getY() + 1);
		}
		if (h < 2) return;
		p.set(pos).setY(pos.getY() + 1);
		int max = h - 1;
		BlockState stem = EdenBlocks.LIMPHIUM.defaultBlockState().setValue(BlockStateProperties.HALF, Half.BOTTOM);
		BlockState top = EdenBlocks.LIMPHIUM.defaultBlockState().setValue(BlockStateProperties.HALF, Half.TOP);
		for (int i = 1; i < max; i++) {
			BlocksHelper.setWithoutUpdate(level, p, stem);
			p.setY(p.getY() + 1);
		}
		BlocksHelper.setWithoutUpdate(level, p, top);
		BlocksHelper.setWithUpdate(level, pos, stem);
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		this.tick(state, world, pos, random);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(state, world, pos, random);
		if (isBonemealSuccess(world, random, pos, state)) {
			performBonemeal(world, random, pos, state);
		}
	}
}
