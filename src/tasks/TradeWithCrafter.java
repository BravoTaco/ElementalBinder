package tasks;

import Utils.PlayerUtilities;
import helpers.Trader;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.*;

public class TradeWithCrafter {
    private Player crafter;
    private Item essence = script.getInventory().getItem(item -> item.getName().equals("Rune essence") || item.getName().equals("Pure essence"));

    public TradeWithCrafter() throws InterruptedException {
        if (savedData.isMule() && (crafter = PlayerUtilities.getPlayerFromNames(savedData.muleNames())) != null && savedData.selectedRune().getRuinsLocation().contains(script.myPlayer())) {
            status = "Trading with crafter!";
            if (Trader.trade(crafter)) {
                if (Trader.offerAll(essence)) {
                    if (Trader.acceptTrade(true)) {
                        amountOfEssencesTraded += 27;
                        if (doesNotHaveEssence()) {
                            new GetEssence();
                        }
                    }
                }
            }
        } else if (PlayerUtilities.getPlayerFromNames(savedData.muleNames()) == null) {
            if (waitForCrafter()) {
                new TradeWithCrafter();
            }
        } else if (!savedData.selectedRune().getRuinsLocation().contains(script.myPlayer())) {
            new WalkToRuins();
        }
    }

    private boolean waitForCrafter() {
        status = "Waiting for crafter!";
        script.log(savedData.runecrafterName());
        new ConditionalSleep(10000, 1500) {
            @Override
            public boolean condition() throws InterruptedException {
                return PlayerUtilities.getPlayerFromNames(savedData.muleNames()) != null;
            }
        }.sleep();
        return PlayerUtilities.getPlayerFromNames(savedData.muleNames()) != null;
    }

    private boolean doesNotHaveEssence() {
        return !script.getInventory().contains("Pure essence") && !script.getInventory().contains("Rune essence")
                || script.getInventory().getAmount("Pure essence") != 27 && script.getInventory().getAmount("Rune essence") != 27;
    }

}
