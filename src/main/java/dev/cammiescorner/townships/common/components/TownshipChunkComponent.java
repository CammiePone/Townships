package dev.cammiescorner.townships.common.components;

import dev.cammiescorner.townships.api.TownshipClaim;
import dev.cammiescorner.townships.api.TownshipsApi;
import dev.cammiescorner.townships.api.components.ChunkComponent;
import dev.cammiescorner.townships.api.components.TownshipComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;

import java.util.UUID;

public class TownshipChunkComponent implements ChunkComponent {
	private final Chunk chunk;
	private UUID claimUuid = Util.NIL_UUID;

	public TownshipChunkComponent(Chunk chunk) {
		this.chunk = chunk;
	}

	@Override
	public TownshipClaim getClaim() {
		if(chunk instanceof WorldChunk worldChunk) {
			World world = worldChunk.getWorld();
			TownshipComponent township = TownshipsApi.getTownshipComponent(world);

			return township.getTownForChunk(chunk);
		}

		return null;
	}

	@Override
	public void setClaim(TownshipClaim claim) {
		if(chunk instanceof WorldChunk worldChunk && worldChunk.getWorld() instanceof ServerWorld world) {
			TownshipComponent township = TownshipsApi.getTownshipComponent(world);
			township.addClaim(world, claim);
		}
	}

	@Override
	public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
		TownshipClaim claim = getClaim();
		buf.writeUuid(claim != null ? claim.getUuid() : Util.NIL_UUID);
	}

	@Override
	public void applySyncPacket(PacketByteBuf buf) {
		claimUuid = buf.readUuid();
	}

	@Override
	public void readFromNbt(NbtCompound tag) {

	}

	@Override
	public void writeToNbt(NbtCompound tag) {

	}

	@Override
	public UUID getClaimUuid() {
		return claimUuid;
	}
}
