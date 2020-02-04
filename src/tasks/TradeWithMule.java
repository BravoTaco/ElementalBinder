package tasks;

import Utils.PlayerUtilities;
import helpers.Trader;
import org.osbot.rs07.api.model.Player;

import static data.GlobalVariables.*;

public class TradeWithMule {
    Player mule;

    public TradeWithMule() throws InterruptedException {
        if ((mule = PlayerUtilities.getPlayerFromNames(savedData.muleNames())) != null) {
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
}
