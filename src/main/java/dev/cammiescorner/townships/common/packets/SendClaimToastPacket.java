package dev.cammiescorner.townships.common.packets;

import dev.cammiescorner.townships.Townships;
import dev.cammiescorner.townships.client.toasts.ClaimToast;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SendClaimToastPacket {
	public static final Identifier ID = Townships.id("send_claim_toast");

	public static void send(ServerPlayerEntity player, Text text) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeText(text);
		ServerPlayNetworking.send(player, ID, buf);
	}

	public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender packetSender) {
		Text text = buf.readText();
		client.submit(() -> client.getToastManager().add(new ClaimToast(text)));
	}
}
