package paulevs.edenring.world.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import org.betterx.wover.generator.api.biomesource.WoverBiomePicker;
import org.betterx.wover.generator.impl.map.hex.HexBiomeMap;
import org.betterx.wover.generator.api.map.BiomeMap;
import paulevs.edenring.EdenRing;
import paulevs.datagen.worldgen.EdenRingBiomesDataProvider;
import paulevs.edenring.noise.InterpolationCell;
import paulevs.edenring.world.biomes.EdenRingBiome;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EdenBiomeSource extends BiomeSource {
	public static final Codec<EdenBiomeSource> CODEC = RecordCodecBuilder.create(
		(instance) -> instance.group(
			RegistryOps.retrieveGetter(Registries.BIOME)
		).apply(instance, instance.stable(EdenBiomeSource::new))
	);
	
	private Map<ChunkPos, InterpolationCell> terrainCache = new ConcurrentHashMap<>();
	private WoverBiomePicker pickerLand;
	private WoverBiomePicker pickerVoid;
	private WoverBiomePicker pickerCave;
	private BiomeMap mapLand;
	private BiomeMap mapVoid;
	private BiomeMap mapCave;

	protected List<Holder<Biome>> biomes;
	
	public EdenBiomeSource(HolderGetter<Biome> biomeRegistry) {
		super();

		biomes = ((HolderLookup<Biome>) biomeRegistry).listElementIds()
			.filter(key -> key.location().getNamespace().equals(EdenRing.MOD_ID))
			.map(biomeRegistry::getOrThrow)
			.collect(Collectors.toList());

		if (pickerLand == null) {
			pickerLand = new WoverBiomePicker((ResourceKey<Biome>) biomeRegistry);
			Iterator<EdenRingBiome> biomeLand = EdenRingBiomesDataProvider.BIOMES_LAND.iterator();
			while (biomeLand.hasNext()) {
				pickerLand.addBiome(biomeLand.next());
			}
			pickerLand.rebuild();
			
			pickerVoid = new WoverBiomePicker((ResourceKey<Biome>) biomeRegistry);
			Iterator<EdenRingBiome> biomeAir = EdenRingBiomesDataProvider.BIOMES_AIR.iterator();
			while (biomeAir.hasNext()) {
				pickerVoid.addBiome(biomeAir.next());
			}
			pickerVoid.rebuild();
			
			pickerCave = new WoverBiomePicker((ResourceKey<Biome>) biomeRegistry);
			Iterator<EdenRingBiome> biomeCave = EdenRingBiomesDataProvider.BIOMES_CAVE.iterator();
			while (biomeCave.hasNext()) {
				pickerCave.addBiome(biomeCave.next());
			}
			pickerCave.rebuild();
		}

		mapLand = new HexBiomeMap(0, GeneratorOptions.biomeSizeLand, pickerLand);
		mapVoid = new HexBiomeMap(0, GeneratorOptions.biomeSizeVoid, pickerVoid);
		mapCave = new HexBiomeMap(0, GeneratorOptions.biomeSizeCave, pickerCave);
	}
	
	@Override
	protected MapCodec<? extends BiomeSource> codec() {
		return CODEC;
	}
	
	@Override
	public Holder<Biome> getNoiseBiome(int x, int y, int z, Sampler sampler) {
		cleanCache(x, z);
		
		int px = (x << 2) | 2;
		int py = (y << 2) | 2;
		int pz = (z << 2) | 2;
		
		ChunkPos chunkPos = new ChunkPos(px >> 4, pz >> 4);
		InterpolationCell cell = terrainCache.get(chunkPos);
		if (cell == null) {
			TerrainGenerator generator = MultiThreadGenerator.getTerrainGenerator();
			cell = new InterpolationCell(generator, 3, 33, 8, 8, new BlockPos(chunkPos.getMinBlockX(), 0, chunkPos.getMinBlockZ()));
			terrainCache.put(chunkPos, cell);
		}
		MutableBlockPos pos = new MutableBlockPos(px, 0, pz);
		
		if (isLand(cell, pos)) {
			if (isCave(cell, pos.setY(py))) {
				return mapCave.getBiome(px, 0, pz).biome;
			}
			return mapLand.getBiome(px, 0, pz).biome;
		}
		return mapVoid.getBiome(px, 0, pz).biome;
	}
	
	public void setSeed(long seed) {
		mapLand = new HexBiomeMap(seed, GeneratorOptions.biomeSizeLand, pickerLand);
		mapVoid = new HexBiomeMap(seed, GeneratorOptions.biomeSizeVoid, pickerVoid);
		mapCave = new HexBiomeMap(seed, GeneratorOptions.biomeSizeCave, pickerCave);
	}
	
	private void cleanCache(int x, int z) {
		if ((x & 63) == 0 && (z & 63) == 0) {
			terrainCache.clear();
			mapLand.clearCache();
			mapVoid.clearCache();
			mapCave.clearCache();
		}
	}
	
	private boolean isLand(InterpolationCell cell, MutableBlockPos pos) {
		for (short py = 0; py < 256; py += 8) {
			if (cell.get(pos.setY(py), false) > -0.05F) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isCave(InterpolationCell cell, MutableBlockPos pos) {
		if (pos.getY() < 8 || pos.getY() > 240) return false;
		boolean v1 = cell.get(pos, false) > 0.0F;
		boolean v2 = cell.get(pos.setY(pos.getY() + 12), false) > 0.0F;
		boolean v3 = cell.get(pos.setY(pos.getY() - 16), false) > 0.0F;
		return v1 && v2 && v3;
	}

	@Override
	protected Stream<Holder<Biome>> collectPossibleBiomes() {
		return biomes.stream();
	}
}
