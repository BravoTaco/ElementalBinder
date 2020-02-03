package tasks;

import helpers.Trader;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.*;

public class TradeWithCrafter {
    private Player crafter;
    private Item essence = script.getInventory().getItem(item -> item.getName().equals("Rune essence") || item.getName().equals("Pure essence"));

    public TradeWithCrafter() throws InterruptedException {
        if (crafterExists() && rune.getRuinsLocation().contains(script.myPlayer())) {
            status = "Trading with crafter!";
            if (Trader.trade(crafter)) {
                if (Trader.offerAll(essence)) {
                    if (Trader.acceptTrade(true)) {
                        if (doesNotHaveEssence()) {
                            new GetEssence();
                        }
                    }
                }
            }
        } else if (!crafterExists()) {
            if (waitForCrafter()) {
                new TradeWithCrafter();
            }
        } else if (!rune.getRuinsLocation().contains(script.myPlayer())) {
            new WalkToRuins();
        }
    }

    private boolean waitForCrafter() {
        status = "Waiting for crafter!";
        script.log(runecrafterName);
        new ConditionalSleep(60000, 3000) {
            @Override
            public boolean condition() throws InterruptedException {
                return crafterExists();
            }
        }.sleep();
        return crafterExists();
    }

    private boolean doesNotHaveEssence() {
        return !script.getInventory().contains("Pure essence") && !script.getInventory().contains("Rune essence")
                || script.getInventory().getAmount("Pure essence") != 27 && script.getInventory().getAmount("Rune essence") != 27;
    }

    private boolean crafterExists() {
        for (Player player : script.getPlayers().getAll()) {
            if (player != null) {
                if (player.getName().equals(runecrafterName)) {
                    crafter = player;
                    return true;
                }
            }
        }
        return false;
    }

}
