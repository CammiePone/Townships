package dev.cammiescorner.townships.common.packets;

import dev.cammiescorner.townships.Townships;
import dev.cammiescorner.townships.api.components.TownshipComponent;
import dev.cammiescorner.townships.client.TownshipsClient;
import dev.cammiescorner.townships.core.util.ClientClaimData;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SyncTownshipPacket {
	public static final Identifier ID = Townships.id("sync_township");

	public static void send(ServerPlayerEntity player, TownshipComponent component) {
		PacketByteBuf buf = PacketByteBufs.create();
		Identifier dimId = player.getServerWorld().getRegistryKey().getValue();
		List<ClientClaimData> list = new ArrayList<>();

		component.getClaims().forEach((identifier, townshipClaim) -> {
			if(identifier.equals(dimId))
				list.add(new ClientClaimData(townshipClaim.getUuid(), townshipClaim.getName()));
		});

		buf.writeVarInt(list.size());
		list.forEach(claimData -> claimData.toByteBuf(buf));

		ServerPlayNetworking.send(player, ID, buf);
	}

	public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender packetSender) {
		Map<UUID, ClientClaimData> map = new Object2ObjectOpenHashMap<>();
		int size = buf.readVarInt();

		for(int i = 0; i < size ; i++) {
			ClientClaimData data = ClientClaimData.of(buf);
			map.put(data.uuid(), data);
		}

		client.submit(() -> {
			TownshipsClient.CLIENT_CLAIM_DATA = map;
		});
	}
}
