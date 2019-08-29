package tasks;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.*;

public class EnterRuins {

    private RS2Object ruins;
    private RS2Object altar;

    public EnterRuins() throws InterruptedException {
        if (!isMule) {
            status = "Entering Ruins!";
            if (ruinsExists()) {
                if (enterRuins()) {
                    if (altarExists()) {
                        if (walkToAltar()) {
                            new UseAltar();
                        }
                    }
                }
            } else {
                if (walkToRuins()) {
                    new EnterRuins();
                }
            }
        } else {
            new TradeWithCrafter();
        }
    }

    private boolean walkToAltar() {
        return script.getWalking().walk(altar);
    }

    private boolean altarExists() {
        return ((altar = script.getObjects().closest("Altar")) != null) && altar.getConfig() == rune.getAltar().getConfig();
    }

    private boolean walkToRuins() {
        return script.getWalking().walk(ruins.getPosition());
    }

    private boolean ruinsExists() {
        return (((ruins = script.getObjects().closest("Mysterious ruins")) != null) && ruins.getPosition().isOnMiniMap(script.getBot()));
    }

    private boolean enterRuins() {
        if (script.getInventory().contains(talisman.getName())) {
            if (script.getInventory().interact("Use", talisman.getName())) {
                if (ruins.interact("Use")) {
                    new ConditionalSleep(5000, 100) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            return script.myPlayer().isMoving();
                        }
                    }.sleep();
                    new ConditionalSleep(15000, 100) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            return (script.getObjects().closest("Altar") != null) && altar.getConfig() == rune.getAltar().getConfig() || !script.myPlayer().isMoving();
                        }
                    }.sleep();
                    return true;
                }
            }
        } else if (script.getEquipment().getItem(tiara.getName()) != null) {
            if (ruins.interact("Enter")) {
                new ConditionalSleep(15000, 100) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return !rune.getRuinsLocation().contains(script.myPlayer());
                    }
                }.sleep();
                return true;
            }
        }
        return false;
    }

}
