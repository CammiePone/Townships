package dev.cammiescorner.townships.client;

import dev.cammiescorner.townships.common.packets.SyncTownshipPacket;
import dev.cammiescorner.townships.core.util.ClientClaimData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class TownshipsClient implements ClientModInitializer {
	public static Map<UUID, ClientClaimData> CLIENT_CLAIM_DATA = Collections.emptyMap();

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(SyncTownshipPacket.ID, SyncTownshipPacket::handle);
	}
}
