package dev.cammiescorner.townships.core.registry;

import dev.cammiescorner.townships.api.TownshipsApi;
import dev.cammiescorner.townships.common.components.TownshipChunkComponent;
import dev.cammiescorner.townships.common.components.TownshipWorldComponent;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer;

public class ModComponents implements LevelComponentInitializer, ChunkComponentInitializer {
	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
		registry.register(TownshipsApi.CHUNK_KEY, TownshipChunkComponent::new);
	}

	@Override
	public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {
		registry.register(TownshipsApi.TOWNSHIP_KEY, worldProperties -> new TownshipWorldComponent());
	}
}
