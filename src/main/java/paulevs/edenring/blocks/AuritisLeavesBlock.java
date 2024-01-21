package paulevs.edenring.blocks;

import org.apache.commons.compress.compressors.lz77support.LZ77Compressor.Block;
import org.betterx.bclib.blocks.BaseLeavesBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.registries.EdenParticles;

public class AuritisLeavesBlock extends BaseLeavesBlock {
	
	@Override
	public Properties properties() {
		// Auto-generated method stub
		return super.properties();
	}

	public AuritisLeavesBlock() {
		super(EdenBlocks.AURITIS_SAPLING, BlockBehaviour.Properties.copy(Block.AZALEA_LEAVES).mapColor(MapColor.GOLD));
	}

	@Override
	public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(128) == 0 && level.getBlockState(pos.below()).isAir()) {
			float dx = random.nextFloat();
			float dz = random.nextFloat();
			level.addParticle(EdenParticles.AURITIS_LEAF_PARTICLE, pos.getX() + dx, pos.getY() - 0.01F, pos.getZ() + dz, 0, 0, 0);
		}
	}
}
