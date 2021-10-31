package dev.cammiescorner.townships.core.util;

import dev.cammiescorner.townships.api.TownshipClaim;

public interface HasLastClaim {
	TownshipClaim getLastClaim();
	void setLastClaim(TownshipClaim lastClaim);
}
