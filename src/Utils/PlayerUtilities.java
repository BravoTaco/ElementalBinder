package Utils;

import org.osbot.rs07.api.model.Player;

import java.util.HashSet;

import static data.GlobalVariables.savedData;
import static data.GlobalVariables.script;

public final class PlayerUtilities {
    private PlayerUtilities() {
    }

    public static Player getPlayerFromNames(HashSet<String> playerNames) {
        for (Player player : script.getPlayers().getAll()) {
            for (String s : savedData.muleNames()) {
                if (StringUtilities.stringMatchesBasedOnChars(s, player.getName())) {
                    return player;
                }
            }
        }
        return null;
    }

}
