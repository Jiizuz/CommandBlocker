package com.github.jiizuz.commandblocker.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Collection;
import java.util.Optional;

/**
 * A {@code Listener} to handle the {@code PlayerCommandPreprocessEvent}
 * event to cancel them if uses a blocked command.
 *
 * @author Jiizuz
 * @see org.bukkit.event.Listener
 * @see org.bukkit.event.player.PlayerCommandPreprocessEvent#setCancelled(boolean)
 * @since 1.0
 */
public class PlayerCommandListener implements Listener {

    /**
     * The message to say to some {@code Player} which uses an {@link #blockedCommands}.
     */
    @SuppressWarnings("SpellCheckingInspection")
    private static final String DISALLOWED_COMMAND = "Comando desconocido.";

    private final Collection<String> blockedCommands;

    /**
     * Constructs a {@code PlayerCommandListener} with the {@code Collection}
     * which contains all the commands to disallow.
     *
     * @param blockedCommands the {@code Collection} containing all
     *                        the disallowed commands in game.
     */
    public PlayerCommandListener(final Collection<String> blockedCommands) {
        this.blockedCommands = blockedCommands;
    }

    /**
     * Parses the {@link PlayerCommandPreprocessEvent#getMessage()} to get
     * the uses command label of the {@param event} and checks if it's
     * present on the {@link #blockedCommands} {@code Collection}
     * <p>
     * If it's present on the {@link #blockedCommands} it will {@code cancel}
     * the {@param event} and sends a message ({@link #DISALLOWED_COMMAND}) to the
     * {@link PlayerCommandPreprocessEvent#getPlayer()}
     *
     * @param event the {@code Event} provided by the {@code Spigot} API.
     * @see Collection#contains(Object)
     * @see org.bukkit.event.Cancellable#setCancelled(boolean)
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerCommandPreprocessEvent(final PlayerCommandPreprocessEvent event) {
        final String commandName = Optional.of(event.getMessage())
                .filter(command -> command.contains(" "))
                // If the command contains arguments, we just get the command label
                .map(command -> command.split(" ")[0])
                // Get the element (command name) removing the first slash '/'
                .orElseGet(() -> event.getMessage().substring(1));

        if (blockedCommands.contains(commandName.toLowerCase())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(DISALLOWED_COMMAND);
        }
    }
}
