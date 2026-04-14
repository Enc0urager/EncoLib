package dev.enco.encolib;

import dev.enco.encolib.actions.argument.FloatArgument;
import dev.enco.encolib.actions.argument.IntArgument;
import dev.enco.encolib.actions.argument.SoundArgument;
import dev.enco.encolib.actions.argument.StringArgument;
import dev.enco.encolib.actions.factory.ActionFactory;
import dev.enco.encolib.actions.model.Action;
import dev.enco.encolib.actions.registry.ActionRegistry;
import dev.enco.encolib.logger.ILogger;
import dev.enco.encolib.logger.LoggerFactory;
import dev.enco.encolib.text.formatting.PlaceholderFormatter;
import dev.enco.encolib.text.message.MessageFactory;
import dev.enco.encolib.text.message.WrappedMessage;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class LibPlugin extends JavaPlugin {
    private final ActionRegistry<Player> playerActionRegistry = new ActionRegistry<>();
    private final ActionFactory<Player> playerActionFactory = new ActionFactory<>(playerActionRegistry);

    @Override
    public void onEnable() {
        ILogger logger = LoggerFactory.newLogger(this);
        logger.info("Registering default actions...");
        registerDefaults();
        logger.info("Default actions have been registered!");
        logger.info("");
        logger.info("EncoLib successfully enabled");
        logger.info("Developer - Encourager, version " + getDescription().getVersion());
    }

    private void registerDefaults() {
        StringArgument messageArg = new StringArgument("message");
        playerActionRegistry.register(new Action<Player>()
                .name("MESSAGE")
                .arguments(messageArg)
                .executor((player, args, keys, values) -> {
                    String message = messageArg.get(args);
                    WrappedMessage msg = MessageFactory.newMessage(message, keys);
                    msg.sendParsed(player, values);
                })
        );

        SoundArgument soundArg = new SoundArgument("sound");
        FloatArgument volumeArg = new FloatArgument("volume");
        FloatArgument pitchArg = new FloatArgument("pitch");
        playerActionRegistry.register(new Action<Player>()
                .name("SOUND")
                .arguments(soundArg, volumeArg, pitchArg)
                .executor((player, args, keys, values) -> {
                    player.playSound(
                            player.getLocation(),
                            soundArg.get(args),
                            volumeArg.get(args),
                            pitchArg.get(args)
                    );
                })
        );

        StringArgument titleArg = new StringArgument("title");
        StringArgument subtitleArg = new StringArgument("subtitle");
        IntArgument fadeInArg = new IntArgument("fadeIn");
        IntArgument stayInArg = new IntArgument("stayIn");
        IntArgument fadeOutArg = new IntArgument("fadeOut");
        playerActionRegistry.register(new Action<Player>()
                .name("TITLE")
                .arguments(titleArg, subtitleArg, fadeInArg, stayInArg, fadeOutArg)
                .executor((player, args, keys, values) -> {
                    player.sendTitle(
                            PlaceholderFormatter.format(titleArg.get(args), keys, values),
                            PlaceholderFormatter.format(subtitleArg.get(args), keys, values),
                            fadeInArg.get(keys),
                            stayInArg.get(keys),
                            fadeOutArg.get(keys)
                    );
                })
        );

        StringArgument actionBarArg = new StringArgument("message");
        playerActionRegistry.register(new Action<Player>()
                .name("ACTION_BAR")
                .arguments(actionBarArg)
                .executor((player, args, keys, values) -> {
                    player.sendActionBar(PlaceholderFormatter.format(actionBarArg.get(args), keys, values));
                })
        );

        StringArgument consoleCommandArg = new StringArgument("command");
        playerActionRegistry.register(new Action<Player>()
                .name("CONSOLE")
                .arguments(consoleCommandArg)
                .executor((player, args, keys, values) -> {
                    Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(),
                            PlaceholderFormatter.format(consoleCommandArg.get(args), keys, values)
                    );
                })
        );

        StringArgument playerCommandArg = new StringArgument("command");
        playerActionRegistry.register(new Action<Player>()
                .name("PLAYER")
                .arguments(playerCommandArg)
                .executor((player, args, keys, values) -> {
                    player.chat(PlaceholderFormatter.format(playerCommandArg.get(args), keys, values));
                })
        );
    }
}
