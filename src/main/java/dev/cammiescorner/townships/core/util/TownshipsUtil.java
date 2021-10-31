package dev.cammiescorner.townships.core.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.cammiescorner.townships.Townships;
import dev.cammiescorner.townships.api.TownshipClaim;
import dev.cammiescorner.townships.api.TownshipsApi;
import dev.cammiescorner.townships.api.components.TownshipComponent;
import dev.cammiescorner.townships.common.packets.SendClaimToastPacket;
import dev.cammiescorner.townships.core.registry.ModExceptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;

import java.util.Map;
import java.util.UUID;

public class TownshipsUtil {
	public static void createTownship(PlayerEntity owner, String name, ServerWorld world) throws CommandSyntaxException {
		TownshipComponent component = TownshipsApi.getTownshipComponent(world);

		if(component.getTownForPlayer(owner) != null)
			throw ModExceptions.ALREADY_IN_TOWN.create();
		if(name.isBlank())
			throw ModExceptions.TOWN_NEEDS_NAME.create();
		if(name.length() > 20)
			throw ModExceptions.TOWN_NAME_TOO_LONG.create(name.length(), 20);
		if(component.getClaims().entries().stream().anyMatch(entry -> entry.getValue().getName().equals(name)))
			throw ModExceptions.TOWN_ALREADY_EXISTS.create(name);

		TownshipClaim claim = new TownshipClaim();
		claim.setUuid(UUID.randomUUID());
		claim.setOwnerUuid(owner.getUuid());
		claim.setName(name);
		claim.addChunkPos(owner.getChunkPos());
		component.addClaim(world, claim);
		((HasLastClaim) owner).setLastClaim(claim);
		SendClaimToastPacket.send((ServerPlayerEntity) owner, new LiteralText(name).formatted(Formatting.AQUA, Formatting.BOLD));
		owner.sendMessage(new TranslatableText("info." + Townships.MOD_ID + ".create_town_success", name), false);
	}

	public static boolean deleteTownship(String name, ServerWorld world) {
		TownshipComponent component = TownshipsApi.getTownshipComponent(world);
		TownshipClaim claim = null;

		for(Map.Entry<Identifier, TownshipClaim> entry : component.getClaims().entries()) {
			if(entry.getValue().getName().equals(name)) {
				claim = entry.getValue();

				world.getPlayers(player -> entry.getValue().getChunkPoses().contains(player.getChunkPos())).forEach(player -> {
					((HasLastClaim) player).setLastClaim(null);
					SendClaimToastPacket.send(player, new TranslatableText("info." + Townships.MOD_ID + ".wilderness").formatted(Formatting.GREEN, Formatting.BOLD));
				});

				break;
			}
		}

		if(claim != null) {
			component.removeClaim(world, claim);
			return true;
		}

		return false;
	}

	public static void addChunkToTownship(PlayerEntity player, ServerWorld world) {
		TownshipComponent component = TownshipsApi.getTownshipComponent(world);
		TownshipClaim claim = component.getTownForPlayer(player);
		ChunkPos chunkPos = player.getChunkPos();

		if(component.getTownForPlayer(player) == null) {
			player.sendMessage(new TranslatableText("error." + Townships.MOD_ID + ".not_in_a_town", chunkPos).formatted(Formatting.RED), false);
			return;
		}

		for(ChunkPos pos : claim.getChunkPoses()) {
			if(pos.x + 1 == chunkPos.x || pos.x - 1 == chunkPos.x || pos.x + 1 == chunkPos.z || pos.z - 1 == chunkPos.z) {
				claim.addChunkPos(chunkPos);
				((HasLastClaim) player).setLastClaim(claim);
				SendClaimToastPacket.send((ServerPlayerEntity) player, new LiteralText(claim.getName()).formatted(Formatting.AQUA, Formatting.BOLD));
				player.sendMessage(new TranslatableText("info." + Townships.MOD_ID + ".add_chunk_success", chunkPos).formatted(Formatting.GREEN), false);
				return;
			}
		}

		player.sendMessage(new TranslatableText("error." + Townships.MOD_ID + ".add_chunk_failure", chunkPos).formatted(Formatting.RED), false);
	}

	public static void removeChunkFromTownship(PlayerEntity player, ServerWorld world) {
		TownshipComponent component = TownshipsApi.getTownshipComponent(world);
		TownshipClaim claim = component.getTownForPlayer(player);
		ChunkPos chunkPos = player.getChunkPos();

		if(claim != null && claim.getChunkPoses().contains(chunkPos)) {
			((HasLastClaim) player).setLastClaim(null);
			SendClaimToastPacket.send((ServerPlayerEntity) player, new TranslatableText("info." + Townships.MOD_ID + ".wilderness").formatted(Formatting.GREEN, Formatting.BOLD));
			claim.removeChunkPos(chunkPos);

			if(claim.getChunkPoses().isEmpty()) {
				player.sendMessage(new TranslatableText("info." + Townships.MOD_ID + ".delete_town_success", claim.getName()), false);
				deleteTownship(claim.getName(), world);
			}
			else {
				player.sendMessage(new TranslatableText("info." + Townships.MOD_ID + ".remove_chunk_success", chunkPos).formatted(Formatting.GREEN), false);
			}
		}
		else if(claim == null) {
			player.sendMessage(new TranslatableText("error." + Townships.MOD_ID + ".not_in_a_town", chunkPos).formatted(Formatting.RED), false);
		}
		else {
			player.sendMessage(new TranslatableText("error." + Townships.MOD_ID + ".remove_chunk_failure", chunkPos).formatted(Formatting.RED), false);
		}
	}
}
