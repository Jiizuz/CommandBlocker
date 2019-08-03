package com.github.jiizuz.commandblocker;

import com.github.jiizuz.commandblocker.listener.PlayerCommandListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;

/**
 * {@code CommandBlocker} is a {@code JavaPlugin} to cancel the needed
 * {@code Event}'s which executes commands, the commands are specified
 * in the file {@code config.yml} as YAML format in List.
 *
 * @author Jiizuz
 * @see PlayerCommandListener
 * @since 1.0
 */
public class CommandBlocker extends JavaPlugin {

    /**
     * The commands to disallow defined on the {@code config.yml} file.
     * <p>
     * We use {@code HashSet} because uses a better structure for {@link Collection#contains(Object)}
     * method, which is what the point of the {@code Collection}
     *
     * @see HashSet#contains(Object)
     * @see <a href="https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-collections">https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-collections</a> for test perfomances.
     */
    private final Collection<String> blockedCommands = new HashSet<>();

    /**
     * Plugin startup logic.
     */
    @Override
    public void onEnable() {
        // Saves if doesn't exists the config.yml file
        saveDefaultConfig();

        loadBlockedCommands();

        registerListeners();
    }

    /**
     * Gets from the {@link #getConfig()} a {@code Collection} of
     * blocked commands to store them for future disallows.
     *
     * @see org.bukkit.configuration.file.YamlConfiguration#getStringList(String)
     * @see Collection#addAll(Collection)
     */
    private void loadBlockedCommands() {
        final Collection<String> blockedCommands = getConfig().getStringList("blocked_commands");

        this.blockedCommands.addAll(blockedCommands);
    }

    /**
     * Register as needed {@code Listener}'s to handle
     * different {@code Event}'s.
     *
     * @see org.bukkit.event.Listener
     */
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerCommandListener(blockedCommands), this);
    }
}
