package tasks;

import helpers.Walker;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.*;

public class GetEssence {

    public GetEssence() throws InterruptedException {
        status = "Getting Essence!";
        if (bankIsOpen()) {
            if (inventoryIsEmptyExceptTalisman()) {
                if (bankContainsPureEssence()) {
                    if (withdrawEssence("Pure essence")) {
                        script.log("Withdrawn: Pure essence");
                        script.getBank().close();
                    }
                } else if (bankContainsRuneEssence()) {
                    if (withdrawEssence("Rune essence")) {
                        script.log("Withdrawn: Rune essence");
                        script.getBank().close();
                    }
                } else {
                    script.log("No essence! Stopping Script!");
                    script.stop(false);
                }
            } else if (depositAllExceptTalisman()) {
                new GetEssence();
            }
        } else if (openBank()) {
            if (waitForBankToOpen()) {
                new GetEssence();
            }
        } else if (!Walker.closestBankContainsPlayer()) {
            if (Walker.walkToBank()) {
                new GetEssence();
            } else {
                if (Walker.webWalkToBank()) {
                    new GetEssence();
                } else {
                    script.log("Unable to walk to bank!");
                }
            }
        }
    }

    private boolean bankIsOpen() {
        return script.getBank().isOpen();
    }

    private boolean inventoryIsEmptyExceptTalisman() {
        return script.getInventory().isEmptyExcept(talisman.getName());
    }

    private boolean depositAllExceptTalisman() {
        return script.getBank().depositAllExcept(talisman.getName());
    }

    private boolean openBank() throws InterruptedException {
        return script.getBank().open();
    }

    private boolean waitForBankToOpen() {
        new ConditionalSleep(10000, 100) {
            @Override
            public boolean condition() throws InterruptedException {
                return script.getBank().isOpen();
            }
        }.sleep();
        return script.getBank().isOpen();
    }

    private boolean bankContainsPureEssence() {
        return script.getBank().contains("Pure essence");
    }

    private boolean bankContainsRuneEssence() {
        return script.getBank().contains("Rune essence");
    }

    private boolean withdrawEssence(String name) {
        if (isMule) {
            return script.getBank().withdraw(name, 27);
        }
        return script.getBank().withdrawAll(name);
    }

}
