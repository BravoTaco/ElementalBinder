package helpers;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.event.WebWalkEvent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import static data.GlobalVariables.*;

public class Walker {
    private static Area closestBank;

    public static Area closestBankToRuins() {
        closestBank = banks[0];
        for (int i = 1; i < banks.length; i++) {
            if (banks[i].getCentralPosition().distance(
                    rune.getRuinsLocation().getCentralPosition()) < closestBank.getCentralPosition().distance(rune.getRuinsLocation().getCentralPosition())) {
                closestBank = banks[i];
            }
        }
        return closestBank;
    }

    public static boolean closestBankContainsPlayer() {
        closestBank = banks[0];
        for (int i = 1; i < banks.length; i++) {
            if (banks[i].getCentralPosition().distance(script.myPosition()) < closestBank.getCentralPosition().distance(script.myPosition())) {
                closestBank = banks[i];
            }
        }
        return closestBank.contains(script.myPlayer());
    }

    public static boolean webWalkToStartupArea() {
        return webWalkToBank();
    }

    public static boolean webWalkToBank() {
        status = "WebWalking to Bank Area!";
        return webWalkingEvent(closestBankToRuins());
    }

    public static boolean webWalkToRuins() {
        status = "WebWalking to Ruins!";
        return webWalkingEvent(rune.getRuinsLocation());
    }

    public static boolean walkToBank() {
        status = "Walking Path to Bank!";
        return walkingEvent(rune.getWalkPaths(), true);
    }

    public static boolean walkToRuins() {
        status = "Walking Path to Ruins!";
        return walkingEvent(rune.getWalkPaths(), false);
    }

    private static boolean webWalkingEvent(Area area) {
        WebWalkEvent webWalkEvent = new WebWalkEvent(area);
        webWalkEvent.setEnergyThreshold(10);
        script.execute(webWalkEvent);
        return webWalkEvent.getStatus().equals(Event.EventStatus.FINISHED);
    }

    private static boolean walkingEvent(Position[][] path, boolean reverse) {
        Random rnd = new Random(System.currentTimeMillis());
        int rndNumber = rnd.nextInt(3);
        LinkedList<Position> posList = new LinkedList<>();
        Position[] walkPath = path[rndNumber];
        Collections.addAll(posList, walkPath);
        if (reverse)
            Collections.reverse(posList);
        WalkingEvent walkingEvent = new WalkingEvent();
        walkingEvent.setPath(posList);
        walkingEvent.setEnergyThreshold(10);
        script.execute(walkingEvent);
        return rune.getRuinsLocation().contains(script.myPlayer());
    }
}
