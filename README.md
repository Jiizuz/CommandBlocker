# CommandBlocker
A Spigot Plugin which cancel the execution of commands in game for Players

This Plugin handles the PlayerCommandPreprocessEvent provided by the Spigot API, to cancel a List of commands defined on the resourse: 'config.yml' in a List format.

The event it's prioritized with the HIGHEST priority, (that means the last on execution) to cancel the event if the executed command it's on the disallowed command List.

Additionally it sends to the executor of a disallowed command, a message meaning (for now) that is a unknown command. (This is for those player who are just typing commands to see current Plugins)
