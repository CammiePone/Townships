package dev.cammiescorner.townships.core.mixin;

import dev.cammiescorner.townships.Townships;
import dev.cammiescorner.townships.api.TownshipClaim;
import dev.cammiescorner.townships.api.TownshipsApi;
import dev.cammiescorner.townships.api.components.TownshipComponent;
import dev.cammiescorner.townships.common.packets.SendClaimToastPacket;
import dev.cammiescorner.townships.core.util.HasLastClaim;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ChunkSectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkTicketManager.class)
public class ChunkTicketManagerMixin {
	@Inject(method = "handleChunkEnter", at = @At("HEAD"))
	public void handleChunkEnter(ChunkSectionPos pos, ServerPlayerEntity player, CallbackInfo info) {
		TownshipComponent component = TownshipsApi.getTownshipComponent(player.world);
		TownshipClaim claim = component.getTownForChunk(player.getServerWorld().getChunk(player.getBlockPos()));
		HasLastClaim entity = (HasLastClaim) player;

		if(claim != null && entity.getLastClaim() != claim)
			SendClaimToastPacket.send(player, new LiteralText(claim.getName()).formatted(Formatting.AQUA, Formatting.BOLD));
		else if(claim == null && entity.getLastClaim() != null)
			SendClaimToastPacket.send(player, new TranslatableText("info." + Townships.MOD_ID + ".wilderness").formatted(Formatting.GREEN, Formatting.BOLD));

		entity.setLastClaim(claim);
	}
}
