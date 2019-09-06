package tasks;

import helpers.Walker;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.*;

public class GetTool {

    public GetTool() throws InterruptedException {
        status = "Getting Talisman or Tiara!";
        if (!inventoryContainsTool()) {
            if (bankIsOpen()) {
                if (!inventoryHasItems()) {
                    if (!playerHasEquipment()) {
                        if (bankContainsTiara()) {
                            if (getTool(tiara.getName())) {
                                if (equipTiara()) {
                                    script.log("Equipped Tiara!");
                                    new GetEssence();
                                }
                            }
                        } else if (bankContainsTalisman()) {
                            if (getTool(talisman.getName())) {
                                script.log("Got Talisman!");
                                new GetEssence();
                            }
                        } else {
                            script.log("Bank does not have required talisman or tiara! Stopping script!");
                            script.stop(false);
                        }
                    } else {
                        if (depositEquipment()) {
                            new GetTool();
                        }
                    }
                } else {
                    if (depositItems()) {
                        new GetTool();
                    }
                }
            } else {
                if (openBank()) {
                    new GetTool();
                } else {
                    if (!Walker.closestBankContainsPlayer()) {
                        if (Walker.walkToBank()) {
                            new GetTool();
                        }
                    }
                }
            }
        } else {
            if (equipTiara()) {
                script.log("Equipped Tiara!");
            } else {
                script.log("Using Talisman!");
            }
        }

    }

    private boolean inventoryContainsTool() {
        return script.getInventory().contains(tiara.getName()) || script.getInventory().contains(talisman.getName());
    }

    private boolean depositItems() {
        return script.getBank().depositAll();
    }

    private boolean depositEquipment() {
        return script.getBank().depositWornItems();
    }

    private boolean openBank() throws InterruptedException {
        return script.getBank().open();
    }

    private boolean equipTiara() {
        if (script.getBank().isOpen()) {
            if (script.getBank().close()) {
                if (script.getInventory().contains(tiara.getName())) {
                    if (script.getInventory().interact("Wear", tiara.getName())) {
                        new ConditionalSleep(10000, 100) {
                            @Override
                            public boolean condition() throws InterruptedException {
                                return !script.getInventory().contains(tiara.getName());
                            }
                        }.sleep();
                        return true;
                    }
                }
            }
        } else {
            if (script.getInventory().contains(tiara.getName())) {
                if (script.getInventory().interact("Wear", tiara.getName())) {
                    new ConditionalSleep(10000, 100) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            return !script.getInventory().contains(tiara.getName());
                        }
                    }.sleep();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean inventoryHasItems() {
        return !script.getInventory().isEmpty();
    }

    private boolean playerHasEquipment() {
        for (int i = 0; i < 11; i++) {
            if (script.getEquipment().getItemInSlot(i) != null) {
                return true;
            }
        }
        return false;
    }

    private boolean bankContainsTiara() {
        return script.getBank().getItem(tiara.getName()) != null;
    }

    private boolean bankContainsTalisman() {
        return script.getBank().getItem(talisman.getName()) != null;
    }

    private boolean bankIsOpen() {
        return script.getBank().isOpen();
    }

    private boolean getTool(String toolName) {
        return script.getBank().withdraw(toolName, 1);
    }

}
