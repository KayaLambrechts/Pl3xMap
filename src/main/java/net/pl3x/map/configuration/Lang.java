package net.pl3x.map.configuration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import net.pl3x.map.Logger;
import net.pl3x.map.Pl3xMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Lang {
    public static String UNKNOWN_SUBCOMMAND = "&cUnknown subcommand";

    public static String WORLD_NOT_SPECIFIED = "&cMust specify a world";
    public static String WORLD_NOT_FOUND = "&cWorld not found";

    public static String RENDER_IN_PROGRESS = "&cA render is already in progress on {world}";
    public static String FULL_RENDER_STARTED = "&aFull render started on {world}";

    public static String VERSION = "&a{name} v{version}";

    public static String LOG_COULD_NOT_CREATE_DIR = "Could not create directory! {path}";
    public static String LOG_COULD_NOT_SAVE_REGION = "Could not save map for region {x},{z}";
    public static String LOG_UNABLE_TO_WRITE_TO_FILE = "Unable to write to {path}";
    public static String LOG_STARTED_FULLRENDER = "§3Started full map render for §e{world}";
    public static String LOG_SCANNING_REGION_FILES = "§eScanning region files...";
    public static String LOG_FOUND_TOTAL_REGION_FILES = "§aFound §7{total} §aregion files";
    public static String LOG_FINISHED_RENDERING = "§3Finished rendering map for §e{world}";
    public static String LOG_SCANNING_REGION_PROGRESS = "§3{progress} §eScanning region {x},{z}";
    public static String LOG_SAVING_CHUNKS_FOR_REGION = "        §aSaving {total} chunks for region {x},{z}";
    public static String LOG_SKIPPING_EMPTY_REGION = "        §cRegion is empty. Skipping. {x},{z}";

    public static String LOG_INTERNAL_WEB_DISABLED = "Internal webserver is disabled in config.yml";
    public static String LOG_INTERNAL_WEB_STARTED = "&aInternal webserver running on {bind}:{port}";
    public static String LOG_INTERNAL_WEB_START_ERROR = "Internal webserver could not start";
    public static String LOG_INTERNAL_WEB_STOPPED = "&aInternal webserver stopped";
    public static String LOG_INTERNAL_WEB_STOP_ERROR = "Internal webserver is not running";

    public static String LOG_JARLOADER_DOWNLOADING = "&eDownloading integrated server jar from &7{url}";
    public static String LOG_JARLOADER_PROGRESS = "     &3progress&7: &e{percent}% &7(&3{current}&e/&3{total}&7)";

    private static void init() {
        UNKNOWN_SUBCOMMAND = getString("unknown-subcommand", UNKNOWN_SUBCOMMAND);

        WORLD_NOT_SPECIFIED = getString("world-not-specified", WORLD_NOT_SPECIFIED);
        WORLD_NOT_FOUND = getString("world-not-found", WORLD_NOT_FOUND);

        RENDER_IN_PROGRESS = getString("render-in-progress", RENDER_IN_PROGRESS);
        FULL_RENDER_STARTED = getString("full-render-started", FULL_RENDER_STARTED);

        VERSION = getString("version", VERSION);

        LOG_COULD_NOT_CREATE_DIR = getString("log.could-not-create-directory", LOG_COULD_NOT_CREATE_DIR);
        LOG_COULD_NOT_SAVE_REGION = getString("log.could-not-save-region", LOG_COULD_NOT_SAVE_REGION);
        LOG_UNABLE_TO_WRITE_TO_FILE = getString("log.unable-to-write-to-file", LOG_UNABLE_TO_WRITE_TO_FILE);
        LOG_STARTED_FULLRENDER = getString("log.started-full-render", LOG_STARTED_FULLRENDER);
        LOG_SCANNING_REGION_FILES = getString("log.scanning-region-files", LOG_SCANNING_REGION_FILES);
        LOG_FOUND_TOTAL_REGION_FILES = getString("log.found-total-region-files", LOG_FOUND_TOTAL_REGION_FILES);
        LOG_FINISHED_RENDERING = getString("log.finished-rendering", LOG_FINISHED_RENDERING);
        LOG_SCANNING_REGION_PROGRESS = getString("log.scanning-region-progress", LOG_SCANNING_REGION_PROGRESS);
        LOG_SAVING_CHUNKS_FOR_REGION = getString("log.saving-chunks-for-region", LOG_SAVING_CHUNKS_FOR_REGION);
        LOG_SKIPPING_EMPTY_REGION = getString("log.skipping-empty-region", LOG_SKIPPING_EMPTY_REGION);

        LOG_INTERNAL_WEB_DISABLED = getString("log.internal-web-disabled", LOG_INTERNAL_WEB_DISABLED);
        LOG_INTERNAL_WEB_STARTED = getString("log.internal-web-started", LOG_INTERNAL_WEB_STARTED);
        LOG_INTERNAL_WEB_START_ERROR = getString("log.internal-web-start-error", LOG_INTERNAL_WEB_START_ERROR);
        LOG_INTERNAL_WEB_STOPPED = getString("log.internal-web-stopped", LOG_INTERNAL_WEB_STOPPED);
        LOG_INTERNAL_WEB_STOP_ERROR = getString("log.internal-web-not-running", LOG_INTERNAL_WEB_STOP_ERROR);

        LOG_JARLOADER_DOWNLOADING = getString("log.dependency-downloading", LOG_JARLOADER_DOWNLOADING);
        LOG_JARLOADER_PROGRESS = getString("log.dependency-progress", LOG_JARLOADER_PROGRESS);
    }

    public static void reload() {
        Pl3xMap plugin = Pl3xMap.getInstance();
        File configFile = new File(plugin.getDataFolder(), Config.LANGUAGE_FILE);
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException ex) {
            Logger.log().log(Level.SEVERE, "Could not load " + Config.LANGUAGE_FILE + ", please correct your syntax errors", ex);
            throw new RuntimeException(ex);
        }
        config.options().header("This is the main language file for " + plugin.getName());
        config.options().copyDefaults(true);

        Lang.init();

        try {
            config.save(configFile);
        } catch (IOException ex) {
            Logger.log().log(Level.SEVERE, "Could not save " + configFile, ex);
        }
    }

    private static YamlConfiguration config;

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return colorize(config.getString(path, config.getString(path)));
    }

    public static void send(CommandSender recipient, String message) {
        if (recipient != null) {
            for (String part : split(message)) {
                recipient.sendMessage(part);
            }
        }
    }

    public static String colorize(String str) {
        if (str == null) {
            return "";
        }
        str = ChatColor.translateAlternateColorCodes('&', str);
        if (ChatColor.stripColor(str).isEmpty()) {
            return "";
        }
        return str;
    }

    public static String[] split(String msg) {
        return msg.split("\n");
    }
}