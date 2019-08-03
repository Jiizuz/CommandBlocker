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
     * We cast our {@code Collection} provided by the
     * {@link org.bukkit.configuration.file.YamlConfiguration#getStringList(String)}
     * to {@code HashSet}, it uses a better structure for {@link Collection#contains(Object)}
     * method, which is what we used for the {@code Collection}
     *
     * @see HashSet#contains(Object)
     * @see <a href="https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-collections">https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-collections</a> for test perfomances.
     */
    private final Collection<String> blockedCommands = new HashSet<>(getConfig().getStringList("blocked_commands"));

    /**
     * Plugin startup logic.
     */
    @Override
    public void onEnable() {
        registerListeners();
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
