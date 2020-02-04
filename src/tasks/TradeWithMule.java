package tasks;

import Utils.StringUtilities;
import helpers.Trader;
import org.osbot.rs07.api.model.Player;

import static data.GlobalVariables.*;

public class TradeWithMule {
    Player mule;

    public TradeWithMule() throws InterruptedException {
        if (muleIsNearby()) {
            status = "Trading with mule!";
            if (Trader.trade(mule)) {
                if (Trader.waitForAccept()) {
                    if (Trader.acceptTrade(false)) {
                        if (Trader.waitForAccept()) {
                            if (Trader.acceptTrade(false)) {
                                if (hasEssence()) {
                                    new EnterRuins();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean hasEssence() {
        return script.getInventory().contains("Rune essence") || script.getInventory().contains("Pure essence");
    }

    private boolean muleIsNearby() {
        for (Player player : script.getPlayers().getAll()) {
            for (String s : savedData.muleNames()) {
                if (StringUtilities.stringMatchesBasedOnChars(s, player.getName())) {
                    script.log("Name Matches!");
                    mule = player;
                    return true;
                }
            }
        }
        return false;
    }
}
