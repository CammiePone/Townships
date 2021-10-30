package dev.cammiescorner.townships.core.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.cammiescorner.townships.Townships;
import dev.cammiescorner.townships.api.TownshipClaim;
import dev.cammiescorner.townships.api.TownshipsApi;
import dev.cammiescorner.townships.api.components.ChunkComponent;
import dev.cammiescorner.townships.core.util.TownshipsUtil;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class ModCommands {
	public static void init(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("township")
				.then(CommandManager.literal("create")
						.then(CommandManager.argument("name", StringArgumentType.greedyString())
								.executes(context -> {
									TownshipsUtil.createTownship(context.getSource().getPlayer(), StringArgumentType.getString(context, "name"), context.getSource().getWorld());

									return Command.SINGLE_SUCCESS;
								})))
				.then(CommandManager.literal("get")
						.executes(context -> {
							ChunkComponent component = TownshipsApi.getChunkComponent(context.getSource().getWorld().getChunk(context.getSource().getPlayer().getBlockPos()));
							TownshipClaim claim = component.getClaim();

							if(claim != null)
								context.getSource().sendFeedback(new TranslatableText("info." + Townships.MOD_ID + ".located_in", claim.getName()), false);
							else
								context.getSource().sendFeedback(new TranslatableText("info." + Townships.MOD_ID + ".in_wilderness"), false);

							return Command.SINGLE_SUCCESS;
						}))
				.then(CommandManager.literal("delete")
						.then(CommandManager.argument("name", StringArgumentType.greedyString())
								.executes(context -> {
									String name = StringArgumentType.getString(context, "name");
									boolean wasDeleted = TownshipsUtil.deleteTownship(name, context.getSource().getWorld());

									if(wasDeleted)
										context.getSource().sendFeedback(new TranslatableText("info." + Townships.MOD_ID + ".delete_town_success", name), false);
									else
										context.getSource().sendFeedback(new TranslatableText("error." + Townships.MOD_ID + ".delete_town_failure", name).formatted(Formatting.RED), false);

									return Command.SINGLE_SUCCESS;
								})))
				.then(CommandManager.literal("leave")
						.executes(context -> {


							return Command.SINGLE_SUCCESS;
						})));
	}
}
