package paulevs.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.betterx.wover.datagen.api.PackBuilder;
import paulevs.datagen.recipes.EdenRingBlockLootTableProvider;
import paulevs.datagen.recipes.EdenRingRecipeDataProvider;
import paulevs.datagen.worldgen.EdenRingBiomesDataProvider;
import paulevs.datagen.worldgen.EdenRingRegistriesDataProvider;

public class EdenRingDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {

        EdenRingBiomesDataProvider.ensureStaticallyLoaded();
        EdenRingRecipeDataProvider.buildRecipes();

        final FabricDataGenerator.Pack pack = dataGenerator.createPack();

        pack.addProvider(EdenRingBiomesDataProvider::new);
        pack.addProvider(EdenRingRecipeDataProvider::new);
        pack.addProvider(EdenRingBlockLootTableProvider::new);
        pack.addProvider(EdenRingBlockTagDataProvider::new);
        pack.addProvider(EdenRingItemTagDataProvider::new);
        pack.addProvider(EdenRingRegistriesDataProvider::new);

    }

                                         @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        EdenRingBiomesDataProvider.ensureStaticallyLoaded();
        EdenRingRegistrySupplier.INSTANCE.bootstrapRegistries(registryBuilder);
        registryBuilder.add(Registries.BIOME, EdenRingBiomesDataProvider::bootstrap);
    }
}
