package tasks;

import helpers.Walker;

import static data.GlobalVariables.*;

public class GetEssence {

    public GetEssence() throws InterruptedException {
        status = "Getting Essence!";
        if (bankIsOpen()) {
            if (inventoryIsEmptyExceptTalisman()) {
                if (bankContainsPureEssence()) {
                    if (withdrawEssence("Pure essence")) {
                        script.log("Withdrawn: Pure essence");
                    }
                } else if (bankContainsRuneEssence()) {
                    if (withdrawEssence("Rune essence")) {
                        script.log("Withdrawn: Rune essence");
                    }
                } else {
                    script.log("No essence! Stopping Script!");
                    script.stop(false);
                }
            } else if (depositAllExceptTalisman()) {
                new GetEssence();
            }
        } else if (openBank()) {
            new GetEssence();
        } else if (!Walker.closestBankContainsPlayer()) {
            if (Walker.bankWalkEvent()) {
                new GetEssence();
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
