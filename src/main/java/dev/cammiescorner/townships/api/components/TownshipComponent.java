package dev.cammiescorner.townships.api.components;

import com.google.common.collect.Multimap;
import dev.cammiescorner.townships.api.TownshipClaim;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

public interface TownshipComponent extends Component {
	TownshipClaim getTownForPlayer(PlayerEntity player);
	TownshipClaim getTownForChunk(ChunkPos pos);
	Multimap<Identifier, TownshipClaim> getClaims();
	void addClaim(ServerWorld world, TownshipClaim claim);

	default TownshipClaim getTownForChunk(Chunk chunk) {
		return getTownForChunk(chunk.getPos());
	}

	void removeClaim(ServerWorld world, TownshipClaim claim);
}
