package dev.cammiescorner.townships;

import dev.cammiescorner.townships.core.util.EventHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Townships implements ModInitializer {
	public static final String MOD_ID = "townships";

	@Override
	public void onInitialize() {
		EventHandler.commonEvents();
	}

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}
}
