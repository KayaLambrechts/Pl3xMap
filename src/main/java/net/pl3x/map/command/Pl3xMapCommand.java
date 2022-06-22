package net.pl3x.map.command;

import net.pl3x.map.Pl3xMap;
import net.pl3x.map.command.commands.CancelRenderCommand;
import net.pl3x.map.command.commands.FullRenderCommand;
import net.pl3x.map.command.commands.HelpCommand;
import net.pl3x.map.command.commands.HideCommand;
import net.pl3x.map.command.commands.ReloadCommand;
import net.pl3x.map.command.commands.ShowCommand;
import net.pl3x.map.command.commands.StatusCommand;

public class Pl3xMapCommand extends BaseCommand {
    public Pl3xMapCommand(Pl3xMap plugin) {
        super(plugin, "map", "Controls the Pl3xMap plugin", "pl3xmap.command.pl3xmap", "");
        registerSubcommand(new CancelRenderCommand(plugin));
        registerSubcommand(new FullRenderCommand(plugin));
        registerSubcommand(new HelpCommand(plugin));
        registerSubcommand(new HideCommand(plugin));
        registerSubcommand(new ReloadCommand(plugin));
        registerSubcommand(new ShowCommand(plugin));
        registerSubcommand(new StatusCommand(plugin));
    }
}