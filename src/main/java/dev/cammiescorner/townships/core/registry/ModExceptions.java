package dev.cammiescorner.townships.core.registry;

import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dev.cammiescorner.townships.Townships;
import net.minecraft.text.TranslatableText;

public class ModExceptions {
	public static final SimpleCommandExceptionType ALREADY_IN_TOWN = new SimpleCommandExceptionType(new TranslatableText("error." + Townships.MOD_ID + ".already_in_town"));
	public static final SimpleCommandExceptionType TOWN_NEEDS_NAME = new SimpleCommandExceptionType(new TranslatableText("error." + Townships.MOD_ID + ".needs_name"));
	public static final DynamicCommandExceptionType TOWN_ALREADY_EXISTS = new DynamicCommandExceptionType(name -> new TranslatableText("error." + Townships.MOD_ID + ".town_exists", name));
}
