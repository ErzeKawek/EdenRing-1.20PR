package paulevs.edenring.world.features.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.bclib.util.MHelper;

public abstract class CeilScatterFeature extends DefaultFeature {
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		WorldGenLevel level = featurePlaceContext.level();
		BlockPos center = featurePlaceContext.origin();
		RandomSource random = featurePlaceContext.random();
		Biome biome = level.getBiome(center).value();
		
		MutableBlockPos pos = center.mutable();
		int maxY = getYOnSurfaceWG(level, center.getX(), center.getZ());
		for (int y = maxY - 1; y > 5; y--) {
			pos.setY(y);
			if (level.getBlockState(pos).isAir()) {
				pos.setY(y + 1);
				if (level.getBlockState(pos).isFaceSturdy(level, pos, Direction.DOWN)) {
					int count = MHelper.randRange(5, 20, random);
					for (int n = 0; n < count; n++) {
						int px = center.getX() + Mth.floor(Mth.clamp(random.nextGaussian() * 2 + 0.5F, -8, 8));
						int pz = center.getZ() + Mth.floor(Mth.clamp(random.nextGaussian() * 2 + 0.5F, -8, 8));
						
						if (level.getBiome(pos.set(px, y, pz)).value() != biome) continue;
						
						for (int i = 5; i > -5; i--) {
							pos.set(px, y + i, pz);
							if (level.getBlockState(pos).isAir()) {
								pos.setY(pos.getY() + 1);
								if (level.getBlockState(pos).isFaceSturdy(level, pos, Direction.DOWN)) {
									pos.setY(pos.getY() - 1);
									generate(level, pos, random);
									break;
								}
							}
						}
					}
				}
			}
		}
		
		return true;
	}
	protected abstract void generate(WorldGenLevel level, MutableBlockPos pos, RandomSource random);
}
