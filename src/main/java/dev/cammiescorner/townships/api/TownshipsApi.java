package dev.cammiescorner.townships.api;

import dev.cammiescorner.townships.Townships;
import dev.cammiescorner.townships.api.components.ChunkComponent;
import dev.cammiescorner.townships.api.components.TownshipComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class TownshipsApi {
	public static final ComponentKey<TownshipComponent> TOWNSHIP_KEY = ComponentRegistry.getOrCreate(Townships.id("township"), TownshipComponent.class);
	public static final ComponentKey<ChunkComponent> CHUNK_KEY = ComponentRegistry.getOrCreate(Townships.id("chunk"), ChunkComponent.class);

	public static TownshipComponent getTownshipComponent(World world) {
		return TOWNSHIP_KEY.get(world.getLevelProperties());
	}

	public static ChunkComponent getChunkComponent(Chunk chunk) {
		return CHUNK_KEY.get(chunk);
	}
}
