package paulevs.edenring.world.biomes;

import org.betterx.wover.biome.api.BiomeKey;
import org.betterx.wover.biome.api.builder.BiomeBootstrapContext;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EdenBiomeKey<C extends EdenRingBiome.Config, PC extends EdenRingBiome.Config> extends BiomeKey<EdenBiomeBuilder> {
    public interface ConfigSupplier<C extends EdenRingBiome.Config> {
        C get(EdenBiomeKey<C, ?> key);
    }

    private final @Nullable EdenBiomeKey<PC, ?> parentOrNull;

    protected EdenBiomeKey(@NotNull ResourceLocation location, @Nullable EdenBiomeKey<PC, ?> parentOrNull) {
        super(location);
        this.parentOrNull = parentOrNull;
    }

    @Override
    @Deprecated
    public EdenBiomeBuilder bootstrap(BiomeBootstrapContext context) {
        throw new IllegalArgumentException("NetherBiomeKey must be bootstrapped with a config");
    }

    @SafeVarargs
    public final EdenBiomeBuilder bootstrap(
            BiomeBootstrapContext context,
            EdenRingBiome.Config config,
            TagKey<Biome>... typeTag
    ) {
        return new EdenBiomeBuilder(context, this, typeTag)
                .parent(this.parentOrNull)
                .configure(config);
    }
}
