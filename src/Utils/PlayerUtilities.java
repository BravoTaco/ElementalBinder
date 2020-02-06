package Utils;

import org.osbot.rs07.api.model.Player;

import java.util.HashSet;

import static data.GlobalVariables.script;

public final class PlayerUtilities {
    private PlayerUtilities() {
    }

    public static Player getPlayerFromNames(HashSet<String> playerNames) {
        for (Player player : script.getPlayers().getAll()) {
            for (String s : playerNames) {
                if (StringUtilities.stringMatchesBasedOnChars(s, player.getName())) {
                    return player;
                }
            }
        }
        return null;
    }

    public static Player getPlayerFromName(String playerName) {
        for (Player player : script.getPlayers().getAll()) {
            if (StringUtilities.stringMatchesBasedOnChars(playerName, player.getName())) {
                return player;
            }
        }
        return null;
    }

}
