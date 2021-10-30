package dev.cammiescorner.townships.api.components;

import dev.cammiescorner.townships.api.TownshipClaim;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.UUID;

public interface ChunkComponent extends AutoSyncedComponent {
	TownshipClaim getClaim();

	void setClaim(TownshipClaim claim);

	UUID getClaimUuid();
}
