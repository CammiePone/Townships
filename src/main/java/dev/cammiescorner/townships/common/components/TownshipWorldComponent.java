package dev.cammiescorner.townships.common.components;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import dev.cammiescorner.townships.api.TownshipClaim;
import dev.cammiescorner.townships.api.TownshipsApi;
import dev.cammiescorner.townships.api.components.TownshipComponent;
import dev.cammiescorner.townships.common.packets.SyncTownshipPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;

import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class TownshipWorldComponent implements TownshipComponent {
	private final Multimap<Identifier, TownshipClaim> claims = MultimapBuilder.hashKeys().arrayListValues().build();

	@Override
	public TownshipClaim getTownForPlayer(PlayerEntity player) {
		for(Map.Entry<Identifier, TownshipClaim> entry : claims.entries())
			if(entry.getValue().getMemberUuids().contains(player.getUuid()))
				return entry.getValue();

		return null;
	}

	@Override
	public TownshipClaim getTownForChunk(ChunkPos pos) {
		for(Map.Entry<Identifier, TownshipClaim> entry : claims.entries())
			if(entry.getValue().getChunkPoses().contains(pos))
				return entry.getValue();

		return null;
	}

	@Override
	public Multimap<Identifier, TownshipClaim> getClaims() {
		return claims;
	}

	@Override
	public void addClaim(ServerWorld world, TownshipClaim claim) {
		claims.put(world.getRegistryKey().getValue(), claim);
		sync(world);
	}

	@Override
	public void removeClaim(ServerWorld world, TownshipClaim claim) {
		claims.entries().removeIf(entry -> entry.getValue() == claim);
		sync(world);
	}

	public void sync(ServerWorld world) {
		TownshipsApi.TOWNSHIP_KEY.sync(world.getLevelProperties());
		PlayerLookup.all(world.getServer()).forEach(player -> SyncTownshipPacket.send(player, this));
	}

	@Override
	public void readFromNbt(NbtCompound tag) {

	}

	@Override
	public void writeToNbt(NbtCompound tag) {

	}
}
