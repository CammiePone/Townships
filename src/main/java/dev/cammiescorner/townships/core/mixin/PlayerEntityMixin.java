package dev.cammiescorner.townships.core.mixin;

import dev.cammiescorner.townships.api.TownshipClaim;
import dev.cammiescorner.townships.core.util.HasLastClaim;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements HasLastClaim {
	@Unique private TownshipClaim lastClaim = null;

	@Override
	public TownshipClaim getLastClaim() {
		return lastClaim;
	}

	@Override
	public void setLastClaim(TownshipClaim lastClaim) {
		this.lastClaim = lastClaim;
	}
}
