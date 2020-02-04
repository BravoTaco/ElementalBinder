package tasks;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.*;

public class EnterRuins {

    private RS2Object ruins;
    private RS2Object altar;

    public EnterRuins() throws InterruptedException {
        if (!savedData.isMule()) {
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
        status = "Walking To altar!";
        return script.getWalking().walk(altar);
    }

    private boolean altarExists() {
        return ((altar = script.getObjects().closest("Altar")) != null) && altar.getConfig() == savedData.selectedRune().getAltar().getConfig();
    }

    private boolean walkToRuins() {
        return script.getWalking().walk(ruins.getPosition());
    }

    private boolean ruinsExists() {
        return (((ruins = script.getObjects().closest("Mysterious ruins")) != null) && ruins.getPosition().isOnMiniMap(script.getBot()));
    }

    private boolean enterRuins() {
        if (script.getInventory().contains(savedData.talisman().getName())) {
            if (script.getInventory().interact("Use", savedData.talisman().getName())) {
                if (ruins.interact("Use")) {
                    new ConditionalSleep(15000, 100) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            return (script.getObjects().closest("Altar") != null) && altar.getConfig() == savedData.selectedRune().getAltar().getConfig();
                        }
                    }.sleep();
                    return true;
                }
            }
        } else if (script.getEquipment().getItem(savedData.tiara().getName()) != null) {
            if (ruins.interact("Enter")) {
                new ConditionalSleep(15000, 100) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return !savedData.selectedRune().getRuinsLocation().contains(script.myPlayer());
                    }
                }.sleep();
                return true;
            }
        }
        return false;
    }

}
