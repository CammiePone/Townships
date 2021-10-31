package dev.cammiescorner.townships.core.registry;

import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dev.cammiescorner.townships.Townships;
import net.minecraft.text.TranslatableText;

public class ModExceptions {
	public static final SimpleCommandExceptionType ALREADY_IN_TOWN = new SimpleCommandExceptionType(new TranslatableText("error." + Townships.MOD_ID + ".already_in_town"));
	public static final SimpleCommandExceptionType TOWN_NEEDS_NAME = new SimpleCommandExceptionType(new TranslatableText("error." + Townships.MOD_ID + ".needs_name"));
	public static final Dynamic2CommandExceptionType TOWN_NAME_TOO_LONG = new Dynamic2CommandExceptionType((length, maxLength) -> new TranslatableText("error." + Townships.MOD_ID + ".name_too_long", length, maxLength));
	public static final DynamicCommandExceptionType TOWN_ALREADY_EXISTS = new DynamicCommandExceptionType(name -> new TranslatableText("error." + Townships.MOD_ID + ".town_exists", name));
}
