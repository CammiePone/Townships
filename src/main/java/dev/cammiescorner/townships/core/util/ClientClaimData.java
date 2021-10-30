package dev.cammiescorner.townships.core.util;

import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public record ClientClaimData(UUID uuid, String name) {
	public void toByteBuf(PacketByteBuf buf) {
		buf.writeUuid(uuid);
		buf.writeString(name);
	}

	public static ClientClaimData of(PacketByteBuf buf) {
		UUID uuid = buf.readUuid();
		String name = buf.readString();

		return new ClientClaimData(uuid, name);
	}
}
