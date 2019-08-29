package helpers;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.event.WebWalkEvent;

import static data.GlobalVariables.*;

public class Walker {
    private static Area closestBank;

    public static boolean closestBankContainsPlayer() {
        closestBank = banks[0];
        for (int i = 1; i < banks.length; i++) {
            if (banks[i].getCentralPosition().distance(script.myPosition()) < closestBank.getCentralPosition().distance(script.myPosition())) {
                closestBank = banks[i];
            }
        }
        return closestBank.contains(script.myPlayer());
    }

    public static boolean bankWalkEvent() {
        WalkingEvent walkingEvent = new WalkingEvent(closestBank);
        walkingEvent.setEnergyThreshold(10);
        walkingEvent.setMinDistanceThreshold(5);
        script.log("Starting WalkingEvent: [BankWalkEvent]");
        script.execute(walkingEvent);
        if (walkingEvent.getStatus().equals(Event.EventStatus.FINISHED)) {
            script.log("WalkingEvent Finished!");
            return true;
        } else {
            script.log("Error With WalkingEvent: [" + walkingEvent.getStatus() + "]");
            script.log("Trying to use WebWalkingEvent instead!");
            WebWalkEvent webWalkEvent = new WebWalkEvent(closestBank);
            webWalkEvent.setEnergyThreshold(10);
            script.execute(webWalkEvent);
            if(webWalkEvent.getStatus().equals(Event.EventStatus.FINISHED)){
                script.log("WebWalkingEvent Finished!");
            } else {
                script.log("Error With WebWalkingEvent[" + webWalkEvent.getStatus() + "]");
                script.stop(false);
            }
        }
        return closestBank.contains(script.myPlayer());
    }

    public static boolean ruinsWalkEvent() {
        WalkingEvent walkingEvent = new WalkingEvent(rune.getRuinsLocation());
        walkingEvent.setEnergyThreshold(10);
        script.log("Starting WalkingEvent: [RuinsWalkEvent]");
        script.execute(walkingEvent);
        if (walkingEvent.getStatus().equals(Event.EventStatus.FINISHED)) {
            script.log("WalkingEvent Finished!");
            return true;
        } else {
            script.log("Error With WalkingEvent: [" + walkingEvent.getStatus() + "]");
            script.log("Trying to use WebWalkingEvent instead!");
            WebWalkEvent webWalkEvent = new WebWalkEvent(rune.getRuinsLocation().getRandomPosition());
            webWalkEvent.setEnergyThreshold(10);
            script.execute(webWalkEvent);
            if(webWalkEvent.getStatus().equals(Event.EventStatus.FINISHED)){
                script.log("WebWalkingEvent Finished!");
            } else {
                script.log("Error With WebWalkingEvent[" + webWalkEvent.getStatus() + "]");
                script.stop(false);
            }
        }
        return rune.getRuinsLocation().contains(script.myPlayer());
    }
}
