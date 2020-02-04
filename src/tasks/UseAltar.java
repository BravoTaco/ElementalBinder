package tasks;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.*;

public class UseAltar {
    RS2Object altar;
    RS2Object portal;

    private long runesInv;

    public UseAltar() throws InterruptedException {
        status = "Using altar!";
        if (altarExists()) {
            if (craftRunes()) {
                if (addRunesCrafted()) {
                    script.log("Crafted runes and Added them to the counter!");
                    if (portalExists()) {
                        status = "Walking To Portal!";
                        if (walkToPortal()) {
                            script.log("Walked To Portal!");
                            new UsePortal();
                        }
                    }
                }
            }
        }
    }

    private boolean walkToPortal() {
        return script.getWalking().walk(portal);
    }

    private boolean portalExists() {
        return (portal = script.getObjects().closest("Portal")) != null && portal.getConfig() == savedData.selectedRune().getPortal().getConfig();
    }

    private boolean addRunesCrafted() {
        if (script.getInventory().contains(savedData.selectedRune().getName())) {
            amountOfRunesMade += (script.getInventory().getAmount(savedData.selectedRune().getName()) - runesInv);
            return true;
        }
        return false;
    }

    private boolean altarExists() {
        return ((altar = script.getObjects().closest("Altar")) != null) && altar.getConfig() == savedData.selectedRune().getAltar().getConfig();
    }

    private boolean craftRunes() {
        if (altar.interact("Craft-rune")) {
            runesInv = script.getInventory().getAmount(savedData.selectedRune().getName());
            new ConditionalSleep(15000, 100) {
                @Override
                public boolean condition() throws InterruptedException {
                    return script.myPlayer().isAnimating();
                }
            }.sleep();
            new ConditionalSleep(15000, 100) {
                @Override
                public boolean condition() throws InterruptedException {
                    return !script.myPlayer().isAnimating();
                }
            }.sleep();
            return true;
        }
        return false;
    }
}
