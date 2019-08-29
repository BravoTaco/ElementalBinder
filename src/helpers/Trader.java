package helpers;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.script;

public class Trader {

    private static RS2Widget inTradeWidget;
    private static RS2Widget amountWidget;
    private static RS2Widget declineButton;
    private static RS2Widget acceptButton;
    private static RS2Widget acceptButtonSelected;

    private static boolean inputXAmount(int amount) {
        new ConditionalSleep(10000, 100) {
            @Override
            public boolean condition() throws InterruptedException {
                return ((amountWidget = script.getWidgets().get(162, 44)) != null) && amountWidget.isVisible();
            }
        }.sleep();
        if (amountWidget != null) {
            return script.keyboard.typeString("" + amount);
        } else {
            return false;
        }
    }

    private static boolean charIsANumber(char c) {
        try {
            Integer.parseInt("" + c);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int availableInvSlots() {
        RS2Widget freeSlotsWidget = script.getWidgets().get(335, 9);
        String amountOfFreeSlots = "0";
        StringBuilder sb = new StringBuilder();
        if (freeSlotsWidget != null && freeSlotsWidget.isVisible()) {
            for (char c : freeSlotsWidget.getMessage().toCharArray()) {
                if (charIsANumber(c)) {
                    sb.append(c);
                }
            }
            amountOfFreeSlots = sb.toString();
        }
        try {
            script.log("Amount of free slots for other player: [" + amountOfFreeSlots + "]");
            return Integer.parseInt(amountOfFreeSlots);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean waitForAccept() {
        new ConditionalSleep(15000, 100) {
            @Override
            public boolean condition() throws InterruptedException {
                return ((acceptButtonSelected = script.getWidgets().getWidgetContainingText("Other player has accepted.")) != null) && acceptButtonSelected.isVisible();
            }
        }.sleep();
        if (acceptButtonSelected != null && acceptButtonSelected.isVisible()) {
            script.log("Accept Button has been clicked by other person!");
            return true;
        }
        return false;
    }

    public static boolean acceptTrade(boolean acceptAllScreens) {
        new ConditionalSleep(5000, 100) {
            @Override
            public boolean condition() throws InterruptedException {
                return ((acceptButton = script.getWidgets().getWidgetContainingText("Accept")) != null) && acceptButton.isVisible();
            }
        }.sleep();
        if (script.getWidgets().get(334, 13) != null) {
            acceptButton = script.getWidgets().get(334, 13);
        }
        if (acceptButton != null && acceptButton.isVisible()) {
            script.log("Accept Button is visible.");
            if (acceptButton.interact("Accept")) {
                script.log("Clicking accept!");
                new ConditionalSleep(10000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return !acceptButton.isVisible();
                    }
                }.sleep();
                if (acceptAllScreens) {
                    acceptTrade(false);
                }
            }
        }
        return true;
    }

    public static boolean declineTrade() {
        if ((declineButton = script.getWidgets().getWidgetContainingText("Decline")) != null) {
            if (declineButton.interact("Decline")) {
                new ConditionalSleep(100000, 100) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return !declineButton.isVisible();
                    }
                }.sleep();
                return true;
            }
        }
        return false;
    }

    public static boolean offerAll(String itemName) {
        if (script.getWidgets().getWidgetContainingText("Trading with: ") != null) {
            return script.getInventory().interact("Offer-All", itemName);
        }
        return false;
    }

    public static boolean offerAll(Item item) {
        if (script.getWidgets().getWidgetContainingText("Trading with: ") != null) {
            return item.interact("Offer-All");
        }
        return false;
    }

    public static boolean offerItem(String itemName, int amount) {
        String amountSelection;
        if (amount == 1) {
            amountSelection = "Offer";
        } else if (amount == 5) {
            amountSelection = "Offer-5";
        } else if (amount == 10) {
            amountSelection = "Offer-10";
        } else {
            amountSelection = "Offer-X";
        }
        if (script.getWidgets().getWidgetContainingText("Trading with: ") != null) {
            if (script.getInventory().interact(amountSelection, itemName)) {
                if (amountSelection.equals("Offer-X")) {
                    return inputXAmount(amount);
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean offerItem(Item item, int amount) {
        if (item == null) {
            return false;
        }
        String amountSelection;
        if (amount == 1) {
            amountSelection = "Offer";
        } else if (amount == 5) {
            amountSelection = "Offer-5";
        } else if (amount == 10) {
            amountSelection = "Offer-10";
        } else {
            amountSelection = "Offer-X";
        }
        if (script.getWidgets().getWidgetContainingText("Trading with: ") != null) {
            if (item.interact(amountSelection)) {
                if (amountSelection.equals("Offer-X")) {
                    return inputXAmount(amount);
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean trade(Player player) {
        if (player == null) {
            return false;
        }
        if (((inTradeWidget = script.getWidgets().getWidgetContainingText("Accept")) != null) && inTradeWidget.isVisible()) {
            script.log("Already in a trade menu!");
            return true;
        }
        if (player.interact("Trade with")) {
            new ConditionalSleep(10000, 100) {
                @Override
                public boolean condition() throws InterruptedException {
                    return ((inTradeWidget = script.getWidgets().getWidgetContainingText("Accept")) != null);
                }
            }.sleep();
            return inTradeWidget != null;
        }
        return false;
    }

    public static boolean trade(String playerName) {
        Player player = script.players.closest((Filter<Player>) player1 -> player1.getName().equals(playerName));
        if (player != null) {
            if (player.interact("Trade with")) {
                new ConditionalSleep(10000, 100) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return ((inTradeWidget = script.getWidgets().getWidgetContainingText("Accept")) != null);
                    }
                }.sleep();
                return inTradeWidget != null;
            }
        }
        return false;
    }


}