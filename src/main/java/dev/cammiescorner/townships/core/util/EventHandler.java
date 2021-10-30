package dev.cammiescorner.townships.core.util;

import dev.cammiescorner.townships.api.TownshipsApi;
import dev.cammiescorner.townships.common.packets.SyncTownshipPacket;
import dev.cammiescorner.townships.core.registry.ModCommands;
import dev.onyxstudios.cca.api.v3.entity.PlayerSyncCallback;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class EventHandler {
	public static void commonEvents() {
		PlayerSyncCallback.EVENT.register(player -> {
			SyncTownshipPacket.send(player, TownshipsApi.getTownshipComponent(player.world));
		});

		CommandRegistrationCallback.EVENT.register(ModCommands::init);
	}
}
