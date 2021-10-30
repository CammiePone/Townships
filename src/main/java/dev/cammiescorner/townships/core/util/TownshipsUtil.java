package dev.cammiescorner.townships.core.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.cammiescorner.townships.Townships;
import dev.cammiescorner.townships.api.TownshipClaim;
import dev.cammiescorner.townships.api.TownshipsApi;
import dev.cammiescorner.townships.api.components.TownshipComponent;
import dev.cammiescorner.townships.core.registry.ModExceptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.UUID;

public class TownshipsUtil {
	public static void createTownship(PlayerEntity owner, String name, ServerWorld world) throws CommandSyntaxException {
		TownshipComponent component = TownshipsApi.getTownshipComponent(world);

		if(component.getTownForPlayer(owner) != null)
			throw ModExceptions.ALREADY_IN_TOWN.create();
		if(name.isBlank())
			throw ModExceptions.TOWN_NEEDS_NAME.create();
		if(component.getClaims().entries().stream().anyMatch(entry -> entry.getValue().getName().equals(name)))
			throw ModExceptions.TOWN_ALREADY_EXISTS.create(name);

		TownshipClaim claim = new TownshipClaim();
		claim.setUuid(UUID.randomUUID());
		claim.setOwnerUuid(owner.getUuid());
		claim.setName(name);
		claim.addChunkPos(owner.getChunkPos());
		component.addClaim(world, claim);
		owner.sendMessage(new TranslatableText("info." + Townships.MOD_ID + ".create_town_success", name), false);
	}

	public static boolean deleteTownship(String name, ServerWorld world) {
		TownshipComponent component = TownshipsApi.getTownshipComponent(world);
		TownshipClaim claim = null;

		for(Map.Entry<Identifier, TownshipClaim> entry : component.getClaims().entries()) {
			if(entry.getValue().getName().equals(name)) {
				claim = entry.getValue();
				break;
			}
		}

		if(claim != null) {
			component.removeClaim(world, claim);
			return true;
		}

		return false;
	}
}
