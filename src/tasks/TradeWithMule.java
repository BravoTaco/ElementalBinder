package tasks;

import helpers.Trader;
import org.osbot.rs07.api.model.Player;

import static data.GlobalVariables.*;

public class TradeWithMule {
    Player mule;

    public TradeWithMule() throws InterruptedException {
        status = "Trading with mule!";
        if (muleExists()) {
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

    private boolean muleExists() {
        for (Player player : script.getPlayers().getAll()) {
            if (player != null) {
                if (muleNames.contains(player.getName()) && rune.getRuinsLocation().contains(player)) {
                    mule = player;
                    return true;
                }
            }
        }
        return false;
    }
}
