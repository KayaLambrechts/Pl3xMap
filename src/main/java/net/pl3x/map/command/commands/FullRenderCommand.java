package net.pl3x.map.command.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.pl3x.map.Pl3xMap;
import net.pl3x.map.command.BaseCommand;
import net.pl3x.map.configuration.Lang;
import net.pl3x.map.render.task.FullRender;
import net.pl3x.map.world.MapWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FullRenderCommand extends BaseCommand {
    public FullRenderCommand(Pl3xMap plugin) {
        super(plugin, "fullrender", Lang.COMMAND_FULLRENDER_DESCRIPTION, "pl3xmap.command.fullrender", "[world]");
    }

    @Override
    protected List<String> handleTabComplete(CommandSender sender, Command command, LinkedList<String> args) {
        if (args != null) {
            return tabMapWorlds(args.peek());
        }
        return Collections.emptyList();
    }

    @Override
    protected void handleCommand(CommandSender sender, Command command, LinkedList<String> args) throws CommandException {
        MapWorld mapWorld = getMapWorld(sender, args);
        if (mapWorld.isRendering()) {
            Lang.send(sender, Lang.COMMAND_FULLRENDER_ALREADY_RENDERING);
            return;
        }
        if (sender instanceof Player) {
            Lang.send(sender, Lang.COMMAND_FULLRENDER_STARTING,
                    Placeholder.parsed("world", mapWorld.getName()));
        }
        mapWorld.startRender(new FullRender(mapWorld));
    }
}